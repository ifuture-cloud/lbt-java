package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.MenuDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author ryanwang
 * @date : 2019-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuVO extends MenuDTO {

    private List<MenuVO> children;
}
