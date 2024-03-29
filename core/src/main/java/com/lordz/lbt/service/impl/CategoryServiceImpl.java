package com.lordz.lbt.service.impl;

import com.lordz.lbt.service.CategoryService;
import com.lordz.lbt.service.PostCategoryService;
import com.lordz.lbt.service.base.AbstractUserCrudService;
import com.lordz.lbt.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.lordz.lbt.exception.AlreadyExistsException;
import com.lordz.lbt.exception.NotFoundException;
import com.lordz.lbt.model.dto.CategoryDTO;
import com.lordz.lbt.model.entity.Category;
import com.lordz.lbt.model.vo.CategoryVO;
import com.lordz.lbt.repository.CategoryRepository;
import com.lordz.lbt.service.base.AbstractCrudService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryService implementation class
 *
 * @author ryanwang
 * @date : 2019-03-14
 */
@Slf4j
@Service
public class CategoryServiceImpl extends AbstractUserCrudService<Category, Integer> implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final PostCategoryService postCategoryService;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               PostCategoryService postCategoryService) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.postCategoryService = postCategoryService;
    }

    @Override
    public Category create(Category category) {
        Assert.notNull(category, "Category to create must not be null");

        // Check the category name
        long count = categoryRepository.countByName(category.getName());

        if (count > 0) {
            log.error("Category has exist already: [{}]", category);
            throw new AlreadyExistsException("该分类已存在");
        }

        // Check parent id
        if (!ServiceUtils.isEmptyId(category.getParentId())) {
            count = categoryRepository.countById(category.getParentId());

            if (count == 0) {
                log.error("Parent category with id: [{}] was not found, category: [{}]", category.getParentId(), category);
                throw new NotFoundException("Parent category with id = " + category.getParentId() + " was not found");
            }
        }

        // Create it
        return super.create(category);
    }

    @Override
    public List<CategoryVO> listAsTree(Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        // List all category
        List<Category> categories = listAll(sort);

        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyList();
        }

        // Create top category
        CategoryVO topLevelCategory = createTopLevelCategory();

        // Concrete the tree
        concreteTree(topLevelCategory, categories);

        return topLevelCategory.getChildren();
    }

    /**
     * Concrete category tree.
     *
     * @param parentCategory parent category vo must not be null
     * @param categories     a list of category
     */
    private void concreteTree(CategoryVO parentCategory, List<Category> categories) {
        Assert.notNull(parentCategory, "Parent category must not be null");

        if (CollectionUtils.isEmpty(categories)) {
            return;
        }

        // Get children for removing after
        List<Category> children = categories.stream()
                .filter(category -> (parentCategory.getId().compareTo(category.getParentId()) == 0))
                .collect(Collectors.toList());

        children.forEach(category -> {
            // Convert to child category vo
            CategoryVO child = new CategoryVO().convertFrom(category);
            // Init children if absent
            if (parentCategory.getChildren() == null) {
                parentCategory.setChildren(new LinkedList<>());
            }
            // Add child
            parentCategory.getChildren().add(child);
        });

        // Remove all child categories
        categories.removeAll(children);

        // Foreach children vos
        if (!CollectionUtils.isEmpty(parentCategory.getChildren())) {
            parentCategory.getChildren().forEach(childCategory -> concreteTree(childCategory, categories));
        }
    }

    /**
     * Creates a top level category.
     *
     * @return top level category with id 0
     */
    @NonNull
    private CategoryVO createTopLevelCategory() {
        CategoryVO topCategory = new CategoryVO();
        // Set default value
        topCategory.setId(0);
        topCategory.setChildren(new LinkedList<>());
        topCategory.setParentId(-1);

        return topCategory;
    }

    /**
     * Get category by slug name
     *
     * @param slugName slug name
     * @return Category
     */
    @Override
    public Category getBySlugName(String slugName) {
        return categoryRepository.getBySlugName(slugName).orElseThrow(() -> new NotFoundException("该分类已存在").setErrorData(slugName));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.getByName(name).orElse(null);
    }

    @Override
    public void removeCategoryAndPostCategoryBy(Integer categoryId) {
        // Remove category
        removeById(categoryId);
        // Remove post categories
        postCategoryService.removeByCategoryId(categoryId);
    }

    @Override
    public CategoryDTO convertTo(Category category) {
        Assert.notNull(category, "Category must not be null");

        return new CategoryDTO().convertFrom(category);
    }

    @Override
    public List<CategoryDTO> convertTo(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyList();
        }

        return categories.stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
    }
}
