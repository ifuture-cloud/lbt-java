package com.lordz.lbt.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.lordz.lbt.model.entity.JournalComment;
import com.lordz.lbt.model.vo.JournalCommentWithJournalVO;
import com.lordz.lbt.service.base.BaseCommentService;

import java.util.List;

/**
 * Journal comment service interface.
 *
 *
 * @date 19-4-25
 */
public interface JournalCommentService extends BaseCommentService<JournalComment> {

    @NonNull
    List<JournalCommentWithJournalVO> convertToWithJournalVo(@Nullable List<JournalComment> journalComments);

    @NonNull
    Page<JournalCommentWithJournalVO> convertToWithJournalVo(@NonNull Page<JournalComment> journalCommentPage);
}
