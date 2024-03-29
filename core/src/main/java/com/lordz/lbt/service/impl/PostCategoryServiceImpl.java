package com.lordz.lbt.service.impl;

import com.lordz.lbt.service.PostCategoryService;
import com.lordz.lbt.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.lordz.lbt.model.dto.CategoryWithPostCountDTO;
import com.lordz.lbt.model.entity.Category;
import com.lordz.lbt.model.entity.Post;
import com.lordz.lbt.model.entity.PostCategory;
import com.lordz.lbt.model.projection.CategoryPostCountProjection;
import com.lordz.lbt.repository.CategoryRepository;
import com.lordz.lbt.repository.PostCategoryRepository;
import com.lordz.lbt.repository.PostRepository;
import com.lordz.lbt.service.base.AbstractCrudService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Post category service implementation.
 *
 *
 * @date 3/19/19
 */
@Service
public class PostCategoryServiceImpl extends AbstractCrudService<PostCategory, Integer> implements PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    public PostCategoryServiceImpl(PostCategoryRepository postCategoryRepository,
                                   PostRepository postRepository,
                                   CategoryRepository categoryRepository) {
        super(postCategoryRepository);
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listCategoryBy(Integer postId) {
        Assert.notNull(postId, "Post id must not be null");

        // Find all category ids
        Set<Integer> categoryIds = postCategoryRepository.findAllCategoryIdsByPostId(postId);

        return categoryRepository.findAllById(categoryIds);
    }

    @Override
    public Map<Integer, List<Category>> listCategoryListMap(Collection<Integer> postIds) {
        if (CollectionUtils.isEmpty(postIds)) {
            return Collections.emptyMap();
        }

        // Find all post categories
        List<PostCategory> postCategories = postCategoryRepository.findAllByPostIdIn(postIds);

        // Fetch category ids
        Set<Integer> categoryIds = ServiceUtils.fetchProperty(postCategories, PostCategory::getCategoryId);

        // Find all categories
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        // Convert to category map
        Map<Integer, Category> categoryMap = ServiceUtils.convertToMap(categories, Category::getId);

        // Create category list map
        Map<Integer, List<Category>> categoryListMap = new HashMap<>();

        // Foreach and collect
        postCategories.forEach(postCategory -> categoryListMap.computeIfAbsent(postCategory.getPostId(), postId -> new LinkedList<>())
                .add(categoryMap.get(postCategory.getCategoryId())));

        return categoryListMap;
    }

    @Override
    public List<Post> listPostBy(Integer categoryId) {
        Assert.notNull(categoryId, "Category id must not be null");

        // Find all post ids
        Set<Integer> postIds = postCategoryRepository.findAllPostIdsByCategoryId(categoryId);

        return postRepository.findAllById(postIds);
    }

    @Override
    public Page<Post> pagePostBy(Integer categoryId, Pageable pageable) {
        Assert.notNull(categoryId, "Category id must not be null");
        Assert.notNull(pageable, "Page info must not be null");

        // Find all post ids
        Set<Integer> postIds = postCategoryRepository.findAllPostIdsByCategoryId(categoryId);

        return postRepository.findAllByIdIn(postIds, pageable);
    }

    @Override
    public List<PostCategory> mergeOrCreateByIfAbsent(Integer postId, Set<Integer> categoryIds) {
        Assert.notNull(postId, "Post id must not be null");

        if (CollectionUtils.isEmpty(categoryIds)) {
            return Collections.emptyList();
        }

        // Build post categories
        List<PostCategory> postCategoriesStaging = categoryIds.stream().map(categoryId -> {
            PostCategory postCategory = new PostCategory();
            postCategory.setPostId(postId);
            postCategory.setCategoryId(categoryId);
            return postCategory;
        }).collect(Collectors.toList());

        List<PostCategory> postCategoriesToCreate = new LinkedList<>();
        List<PostCategory> postCategoriesToRemove = new LinkedList<>();

        // Find all exist post categories
        List<PostCategory> postCategories = postCategoryRepository.findAllByPostId(postId);

        postCategories.forEach(postCategory -> {
            if (!postCategoriesStaging.contains(postCategory)) {
                postCategoriesToRemove.add(postCategory);
            }
        });

        postCategoriesStaging.forEach(postCategoryStaging -> {
            if (!postCategories.contains(postCategoryStaging)) {
                postCategoriesToCreate.add(postCategoryStaging);
            }
        });

        // Remove post categories
        removeAll(postCategoriesToRemove);

        // Remove all post categories need to remove
        postCategories.removeAll(postCategoriesToRemove);

        // Add all created post categories
        postCategories.addAll(createInBatch(postCategoriesToCreate));

        // Create them
        return postCategories;
    }

    @Override
    public List<PostCategory> listByPostId(Integer postId) {
        Assert.notNull(postId, "Post id must not be null");

        return postCategoryRepository.findAllByPostId(postId);
    }

    @Override
    public List<PostCategory> listByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId, "Category id must not be null");

        return postCategoryRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Set<Integer> listCategoryIdsByPostId(Integer postId) {
        Assert.notNull(postId, "Post id must not be null");

        return postCategoryRepository.findAllCategoryIdsByPostId(postId);
    }

    @Override
    public List<PostCategory> removeByPostId(Integer postId) {
        Assert.notNull(postId, "PoremoveByIdst id must not be null");

        return postCategoryRepository.deleteByPostId(postId);
    }

    @Override
    public List<PostCategory> removeByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId, "Category id must not be null");

        return postCategoryRepository.deleteByCategoryId(categoryId);
    }

    @Override
    public List<CategoryWithPostCountDTO> listCategoryWithPostCountDto(Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        List<Category> categories = categoryRepository.findAll(sort);

        // Query category post count
        Map<Integer, Long> categoryPostCountMap = ServiceUtils.convertToMap(postCategoryRepository.findPostCount(), CategoryPostCountProjection::getCategoryId, CategoryPostCountProjection::getPostCount);

        // Convert and return
        return categories.stream()
                .map(category -> {
                    // Create category post count dto
                    CategoryWithPostCountDTO categoryWithPostCountDTO = new CategoryWithPostCountDTO().convertFrom(category);
                    // Set post count
                    categoryWithPostCountDTO.setPostCount(categoryPostCountMap.getOrDefault(category.getId(), 0L));
                    return categoryWithPostCountDTO;
                })
                .collect(Collectors.toList());
    }
}
