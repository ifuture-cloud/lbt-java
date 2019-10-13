package com.lordz.lbt.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.lordz.lbt.model.entity.SheetComment;
import com.lordz.lbt.model.vo.SheetCommentWithSheetVO;
import com.lordz.lbt.service.base.BaseCommentService;

import java.util.List;

/**
 * Sheet comment service interface.
 *
 *
 * @date 19-4-24
 */
public interface SheetCommentService extends BaseCommentService<SheetComment> {

    @NonNull
    List<SheetCommentWithSheetVO> convertToWithPostVo(@Nullable List<SheetComment> sheetComments);

    @NonNull
    Page<SheetCommentWithSheetVO> convertToWithPostVo(@NonNull Page<SheetComment> sheetCommentPage);
}
