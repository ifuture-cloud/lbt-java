package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.CategoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Category vo.
 *
 *
 * @date 3/21/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryVO extends CategoryDTO {

    private List<CategoryVO> children;
}
