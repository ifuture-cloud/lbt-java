package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.BaseCommentDTO;
import com.lordz.lbt.model.dto.JournalDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Journal comment with journal vo.
 *
 *
 * @date 19-4-25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JournalCommentWithJournalVO extends BaseCommentDTO {

    private JournalDTO journal;
}
