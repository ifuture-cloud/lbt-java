package com.lordz.lbt.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.entity.JournalComment;

/**
 * Journal comment param.
 *
 *
 * @date 3/22/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JournalCommentParam extends BaseCommentParam<JournalComment> {

}
