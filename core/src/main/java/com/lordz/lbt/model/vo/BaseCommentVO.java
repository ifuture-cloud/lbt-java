package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.BaseCommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Base comment vo.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseCommentVO extends BaseCommentDTO {

    List<BaseCommentVO> children;
}
