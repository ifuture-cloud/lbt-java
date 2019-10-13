package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.base.OutputConverter;

/**
 * Menu output dto.
 *
 *
 * @date 4/3/19
 */
@Data
@EqualsAndHashCode
@ToString
public class MenuDTO implements OutputConverter<MenuDTO, Menu> {

    private Integer id;

    private String name;

    private String url;

    private Integer priority;

    private String target;

    private String icon;

    private Integer parentId;
}
