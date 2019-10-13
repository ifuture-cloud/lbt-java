package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Link;
import lombok.Data;
import com.lordz.lbt.model.dto.base.OutputConverter;

/**
 * Link output dto.
 *
 * @author ryanwang
 * @date : 2019/3/21
 */
@Data
public class LinkDTO implements OutputConverter<LinkDTO, Link> {

    private Integer id;

    private String name;

    private String url;

    private String logo;

    private String description;

    private String team;
}
