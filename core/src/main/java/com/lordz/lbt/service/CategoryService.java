package com.lordz.lbt.service;

import com.lordz.lbt.service.base.UserCrudService;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import com.lordz.lbt.model.dto.CategoryDTO;
import com.lordz.lbt.model.entity.Category;
import com.lordz.lbt.model.vo.CategoryVO;
import com.lordz.lbt.service.base.CrudService;

import java.util.List;

/**
 * Category service.
 *
 *
 */
public interface CategoryService extends UserCrudService<Category, Integer> {

    /**
     * Lists as category tree.
     *
     * @param sort sort info must not be null
     * @return a category tree
     */
    @NonNull
    List<CategoryVO> listAsTree(@NonNull Sort sort);

    /**
     * Get category by slug name
     *
     * @param slugName slug name
     * @return Category
     */
    @NonNull
    Category getBySlugName(@NonNull String slugName);

    /**
     * Get Category by name.
     *
     * @param name name
     * @return Category
     */
    @Nullable
    Category getByName(@NonNull String name);

    /**
     * Removes category and post categories.
     *
     * @param categoryId category id must not be null
     */
    @Transactional
    void removeCategoryAndPostCategoryBy(Integer categoryId);


    /**
     * Converts to category dto.
     *
     * @param category category must not be null
     * @return category dto
     */
    @NonNull
    CategoryDTO convertTo(@NonNull Category category);

    /**
     * Converts to category dto list.
     *
     * @param categories category list
     * @return a list of category dto
     */
    @NonNull
    List<CategoryDTO> convertTo(@Nullable List<Category> categories);
}
