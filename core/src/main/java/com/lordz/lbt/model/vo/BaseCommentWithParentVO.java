package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.BaseCommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Base comment with parent comment vo.
 *
 *
 * @date 3/31/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class BaseCommentWithParentVO extends BaseCommentDTO implements Cloneable {

    /**
     * Parent comment.
     */
    private BaseCommentWithParentVO parent;

    public BaseCommentWithParentVO clone() {
        try {
            return (BaseCommentWithParentVO) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("Clone not support exception", e);
            return null;
        }
    }
}
