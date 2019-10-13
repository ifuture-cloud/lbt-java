package com.lordz.lbt.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category with post count dto.
 *
 *
 * @date 19-4-23
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryWithPostCountDTO extends CategoryDTO {

    private Long postCount;
}
