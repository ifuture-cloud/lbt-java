package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Category output dto.
 *
 *
 * @date 3/19/19
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryDTO implements OutputConverter<CategoryDTO, Category> {

    private Integer id;

    private String name;

    private String slugName;

    private String description;

    private Integer parentId;

    private Date createTime;
}
