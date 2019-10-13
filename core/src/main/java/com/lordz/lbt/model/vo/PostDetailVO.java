package com.lordz.lbt.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lordz.lbt.model.dto.post.BasePostDetailDTO;

import java.util.Set;

/**
 * Post vo.
 *
 *
 * @date 3/21/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostDetailVO extends BasePostDetailDTO {

    private Set<Integer> tagIds;

    private Set<Integer> categoryIds;
}
