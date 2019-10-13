package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseRepository;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.entity.Menu;

/**
 * Menu repository.
 *
 *
 */
public interface MenuRepository extends BaseRepository<Menu, Integer> {

    boolean existsByName(@NonNull String name);

    boolean existsByIdNotAndName(@NonNull Integer id, @NonNull String name);
}
