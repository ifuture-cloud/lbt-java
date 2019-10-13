package com.lordz.lbt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.lordz.lbt.model.entity.PostComment;
import com.lordz.lbt.model.params.CommentQuery;
import com.lordz.lbt.model.vo.PostCommentWithPostVO;
import com.lordz.lbt.service.base.BaseCommentService;

import java.util.List;

/**
 * PostComment service.
 *
 *
 */
public interface PostCommentService extends BaseCommentService<PostComment> {

    /**
     * Converts to with post vo.
     *
     * @param commentPage comment page must not be null
     * @return a page of comment with post vo
     */
    @NonNull
    Page<PostCommentWithPostVO> convertToWithPostVo(@NonNull Page<PostComment> commentPage);

    /**
     * Converts to with post vo
     *
     * @param postComments comment list
     * @return a list of comment with post vo
     */
    @NonNull
    List<PostCommentWithPostVO> convertToWithPostVo(@Nullable List<PostComment> postComments);

    @NonNull
    Page<PostCommentWithPostVO> pageTreeBy(@NonNull CommentQuery commentQuery, @NonNull Pageable pageable);
}
