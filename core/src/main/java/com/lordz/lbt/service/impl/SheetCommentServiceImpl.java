package com.lordz.lbt.service.impl;

import com.lordz.lbt.model.entity.BaseEntity;
import com.lordz.lbt.model.entity.UserBaseEntity;
import com.lordz.lbt.service.OptionService;
import com.lordz.lbt.service.SheetCommentService;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.ServiceUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.lordz.lbt.exception.BadRequestException;
import com.lordz.lbt.exception.NotFoundException;
import com.lordz.lbt.model.dto.post.BasePostMinimalDTO;
import com.lordz.lbt.model.entity.Sheet;
import com.lordz.lbt.model.entity.SheetComment;
import com.lordz.lbt.model.vo.SheetCommentWithSheetVO;
import com.lordz.lbt.repository.SheetCommentRepository;
import com.lordz.lbt.repository.SheetRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sheet comment service implementation.
 *
 *
 * @date 19-4-24
 */
@Service
public class SheetCommentServiceImpl extends BaseCommentServiceImpl<SheetComment> implements SheetCommentService {

    private final SheetCommentRepository sheetCommentRepository;

    private final SheetRepository sheetRepository;

    public SheetCommentServiceImpl(SheetCommentRepository sheetCommentRepository,
                                   OptionService optionService,
                                   UserService userService,
                                   ApplicationEventPublisher eventPublisher,
                                   SheetRepository sheetRepository) {
        super(sheetCommentRepository, optionService, userService, eventPublisher);
        this.sheetCommentRepository = sheetCommentRepository;
        this.sheetRepository = sheetRepository;
    }

    @Override
    public UserBaseEntity validateTarget(Integer sheetId) {
        Sheet sheet = sheetRepository.findById(sheetId)
                .orElseThrow(() -> new NotFoundException("该页面不存在或已删除").setErrorData(sheetId));

        if (sheet.getDisallowComment()) {
            throw new BadRequestException("该页面已被禁止评论").setErrorData(sheetId);
        }
        return sheet;
    }

    @Override
    public List<SheetCommentWithSheetVO> convertToWithPostVo(List<SheetComment> sheetComments) {
        if (CollectionUtils.isEmpty(sheetComments)) {
            return Collections.emptyList();
        }

        Set<Integer> sheetIds = ServiceUtils.fetchProperty(sheetComments, SheetComment::getPostId);

        Map<Integer, Sheet> sheetMap = ServiceUtils.convertToMap(sheetRepository.findAllById(sheetIds), Sheet::getId);

        return sheetComments.stream()
                .filter(comment -> sheetMap.containsKey(comment.getPostId()))
                .map(comment -> {
                    SheetCommentWithSheetVO sheetCmtWithPostVO = new SheetCommentWithSheetVO().convertFrom(comment);
                    sheetCmtWithPostVO.setSheet(new BasePostMinimalDTO().convertFrom(sheetMap.get(comment.getPostId())));
                    return sheetCmtWithPostVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<SheetCommentWithSheetVO> convertToWithPostVo(Page<SheetComment> sheetCommentPage) {
        Assert.notNull(sheetCommentPage, "Sheet comment page must not be null");

        return new PageImpl<>(convertToWithPostVo(sheetCommentPage.getContent()), sheetCommentPage.getPageable(), sheetCommentPage.getTotalElements());

    }
}
