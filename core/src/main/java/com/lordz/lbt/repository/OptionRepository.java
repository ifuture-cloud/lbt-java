package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseRepository;
import com.lordz.lbt.model.entity.Option;

import java.util.Optional;

/**
 * Option repository.
 *
 *
 */
public interface OptionRepository extends BaseRepository<Option, Integer> {

    /**
     * Query option by key
     *
     * @param key key
     * @return Option
     */
    Optional<Option> findByKey(String key);

    /**
     * Delete option by key
     *
     * @param key key
     */
    void deleteByKey(String key);
}
