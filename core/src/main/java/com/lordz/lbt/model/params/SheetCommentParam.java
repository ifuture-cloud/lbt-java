package com.lordz.lbt.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.entity.SheetComment;

/**
 * Sheet comment param.
 *
 *
 * @date 19-4-25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetCommentParam extends BaseCommentParam<SheetComment> {

}
