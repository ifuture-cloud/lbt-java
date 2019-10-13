package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseCommentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.entity.SheetComment;
import com.lordz.lbt.model.projection.CommentChildrenCountProjection;
import com.lordz.lbt.model.projection.CommentCountProjection;

import java.util.List;

/**
 * Sheet comment repository.
 *
 *
 * @date 19-4-24
 */
public interface SheetCommentRepository extends BaseCommentRepository<SheetComment> {

    @Query("select new com.lordz.lbt.model.projection.CommentCountProjection(count(comment.id), comment.postId) " +
            "from SheetComment comment " +
            "where comment.postId in ?1 group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByPostIds(@NonNull Iterable<Integer> postIds);

    @Query("select new com.lordz.lbt.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) " +
            "from SheetComment comment " +
            "where comment.parentId in ?1 " +
            "group by comment.parentId")
    @NonNull
    List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Iterable<Long> commentIds);
}
