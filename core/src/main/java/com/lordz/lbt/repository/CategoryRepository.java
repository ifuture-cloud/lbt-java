package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseRepository;
import com.lordz.lbt.repository.base.UserBaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Category repository.
 *
 *
 */
public interface CategoryRepository extends UserBaseRepository<Category, Integer> {

    /**
     * Counts by category name.
     *
     * @param name category name must not be blank
     * @return the count
     */
    long countByName(@NonNull String name);

    /**
     * Counts by category id.
     *
     * @param id category id must not be null
     * @return the count
     */
    long countById(@NonNull Integer id);

    /**
     * Get category by slug name
     *
     * @param slugName slug name
     * @return Optional of Category
     */
    Optional<Category> getBySlugName(@NonNull String slugName);

    /**
     * Get category by name.
     *
     * @param name name
     * @return Optional of Category
     */
    Optional<Category> getByName(@NonNull String name);



}
