package com.lordz.lbt.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.lordz.lbt.model.entity.BaseComment;
import com.lordz.lbt.model.enums.CommentStatus;
import com.lordz.lbt.model.projection.CommentChildrenCountProjection;
import com.lordz.lbt.model.projection.CommentCountProjection;

import java.util.List;

/**
 * Base comment repository.
 *
 *
 * @date 19-4-24
 */
@NoRepositoryBean
public interface BaseCommentRepository<COMMENT extends BaseComment> extends BaseRepository<COMMENT, Long>, JpaSpecificationExecutor<COMMENT> {

    /**
     * Finds all comments by status.
     *
     * @param status   status must not be null
     * @param pageable page info must not be null
     * @return a page of comment
     */
    @NonNull
    Page<COMMENT> findAllByStatus(@Nullable CommentStatus status, @NonNull Pageable pageable);


    /**
     * Finds all comments by post ids.
     *
     * @param postIds post ids must not be null
     * @return a list of comment
     */
    @NonNull
    List<COMMENT> findAllByPostIdIn(@NonNull Iterable<Integer> postIds);

    /**
     * Finds all comments by post id.
     *
     * @param postId post id must not be null
     * @return a list of comment
     */
    @NonNull
    List<COMMENT> findAllByPostId(@NonNull Integer postId);

    /**
     * Counts comment count by post id collection.
     *
     * @param postIds post id collection must not be null
     * @return a list of comment count
     */
    @Query("select new com.lordz.lbt.model.projection.CommentCountProjection(count(comment.id), comment.postId) " +
            "from BaseComment comment " +
            "where comment.postId in ?1 " +
            "group by comment.postId")
    @NonNull
    List<CommentCountProjection> countByPostIds(@NonNull Iterable<Integer> postIds);

    /**
     * Counts by comment status.
     *
     * @param status comment status must not be null
     * @return comment count
     */
    long countByStatus(@NonNull CommentStatus status);

    /**
     * Finds comments by post id, comment status.
     *
     * @param postId post id must not be null
     * @param status comment status must not be null
     * @return a list of comment
     */
    @NonNull
    List<COMMENT> findAllByPostIdAndStatus(Integer postId, CommentStatus status);

    /**
     * Finds comments by post id and comment status.
     *
     * @param postId   post id must not be null
     * @param status   comment status must not be null
     * @param pageable page info must not be null
     * @return a page of comment
     */
    @NonNull
    Page<COMMENT> findAllByPostIdAndStatus(Integer postId, CommentStatus status, Pageable pageable);

    /**
     * Finds comments by post id, comment status and parent id.
     *
     * @param postId   post id must not be null
     * @param status   comment status must not be null
     * @param parentId comment parent id must not be null
     * @return a list of comment
     */
    @NonNull
    List<COMMENT> findAllByPostIdAndStatusAndParentId(Integer postId, CommentStatus status, Long parentId);

    /**
     * Finds all comments by status and parent id collection.
     *
     * @param status    comment status must not be null
     * @param parentIds parent id collection must not be null
     * @return a list of comment
     */
    @NonNull
    List<COMMENT> findAllByStatusAndParentIdIn(@NonNull CommentStatus status, @NonNull Iterable<Long> parentIds);

    /**
     * Finds comments by post id, comment status and parent id.
     *
     * @param postId   post id must not be null
     * @param status   comment status must not be null
     * @param parentId comment parent id must not be null
     * @param pageable page info must not be null
     * @return a page of comment
     */
    @NonNull
    Page<COMMENT> findAllByPostIdAndStatusAndParentId(Integer postId, CommentStatus status, Long parentId, Pageable pageable);


    @Query("select new com.lordz.lbt.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) " +
            "from BaseComment comment " +
            "where comment.parentId in ?1 " +
            "group by comment.parentId")
    @NonNull
    List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Iterable<Long> commentIds);
}
