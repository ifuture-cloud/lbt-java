package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.lordz.lbt.model.entity.Journal;

/**
 * Journal repository.
 *
 *
 * @date 3/22/19
 */
public interface JournalRepository extends BaseRepository<Journal, Integer>, JpaSpecificationExecutor<Journal> {

}
