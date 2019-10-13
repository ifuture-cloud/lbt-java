package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.lordz.lbt.model.entity.Photo;

import java.util.List;

/**
 * Photo repository.
 *
 *
 */
public interface PhotoRepository extends BaseRepository<Photo, Integer>, JpaSpecificationExecutor<Photo> {

    /**
     * Query photos by team
     *
     * @param team team
     * @param sort sort
     * @return list of photo
     */
    List<Photo> findByTeam(String team, Sort sort);
}
