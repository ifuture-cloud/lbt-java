package com.lordz.lbt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import com.lordz.lbt.model.dto.CategoryWithPostCountDTO;
import com.lordz.lbt.model.entity.Category;
import com.lordz.lbt.model.entity.Post;
import com.lordz.lbt.model.entity.PostCategory;
import com.lordz.lbt.service.base.CrudService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Post category service interface.
 *
 *
 * @date 3/19/19
 */
public interface PostCategoryService extends CrudService<PostCategory, Integer> {

    /**
     * Lists category by post id.
     *
     * @param postId post id must not be null
     * @return a list of category
     */
    @NonNull
    List<Category> listCategoryBy(@NonNull Integer postId);

    /**
     * List category list map by post id collection.
     *
     * @param postIds post id collection
     * @return a category list map (key: postId, value: a list of category)
     */
    @NonNull
    Map<Integer, List<Category>> listCategoryListMap(@Nullable Collection<Integer> postIds);

    /**
     * Lists post by category id.
     *
     * @param categoryId category id must not be null
     * @return a list of post
     */
    @NonNull
    List<Post> listPostBy(@NonNull Integer categoryId);

    /**
     * Pages post by category slug name.
     *
     * @param categoryId category id must not be null
     * @param pageable   pageable
     * @return page of post
     */
    @NonNull
    Page<Post> pagePostBy(@NonNull Integer categoryId, Pageable pageable);

    /**
     * Merges or creates post categories by post id and category id set if absent.
     *
     * @param postId      post id must not be null
     * @param categoryIds category id set
     * @return a list of post category
     */
    @NonNull
    List<PostCategory> mergeOrCreateByIfAbsent(@NonNull Integer postId, @Nullable Set<Integer> categoryIds);

    /**
     * Lists by post id.
     *
     * @param postId post id must not be null
     * @return a list of post category
     */
    @NonNull
    List<PostCategory> listByPostId(@NonNull Integer postId);

    /**
     * Lists by category id.
     *
     * @param categoryId category id must not be null
     * @return a list of post category
     */
    @NonNull
    List<PostCategory> listByCategoryId(@NonNull Integer categoryId);

    /**
     * List category id set by post id.
     *
     * @param postId post id must not be null
     * @return a set of category id
     */
    @NonNull
    Set<Integer> listCategoryIdsByPostId(@NonNull Integer postId);

    /**
     * Removes post categories by post id.
     *
     * @param postId post id must not be null
     * @return a list of post category deleted
     */
    @NonNull
    @Transactional
    List<PostCategory> removeByPostId(@NonNull Integer postId);

    /**
     * Removes post categories by category id.
     *
     * @param categoryId category id must not be null
     * @return a list of post category deleted
     */
    @NonNull
    @Transactional
    List<PostCategory> removeByCategoryId(@NonNull Integer categoryId);

    /**
     * Lists category with post count.
     *
     * @param sort sort info
     * @return a list of category dto
     */
    @NonNull
    List<CategoryWithPostCountDTO> listCategoryWithPostCountDto(@NonNull Sort sort);
}
