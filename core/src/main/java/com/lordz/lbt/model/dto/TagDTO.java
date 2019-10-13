package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Tag;
import lombok.Data;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Tag output dto.
 *
 *
 * @date 3/19/19
 */
@Data
public class TagDTO implements OutputConverter<TagDTO, Tag> {

    private Integer id;

    private String name;

    private String slugName;

    private Date createTime;
}
