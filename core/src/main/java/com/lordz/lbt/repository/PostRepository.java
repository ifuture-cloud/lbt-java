package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BasePostRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.lordz.lbt.model.entity.Post;


/**
 * Post repository.
 *
 */
public interface PostRepository extends BasePostRepository<Post>, JpaSpecificationExecutor<Post> {

    @Override
    @Query("select sum(p.visits) from Post p")
    Long countVisit();

    @Override
    @Query("select sum(p.likes) from Post p")
    Long countLike();

}
