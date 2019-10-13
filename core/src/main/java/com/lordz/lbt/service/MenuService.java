package com.lordz.lbt.service;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.dto.MenuDTO;
import com.lordz.lbt.model.entity.Menu;
import com.lordz.lbt.model.params.MenuParam;
import com.lordz.lbt.model.vo.MenuVO;
import com.lordz.lbt.service.base.CrudService;

import java.util.List;

/**
 * Menu service.
 *
 *
 * @author ryanwang
 */
public interface MenuService extends CrudService<Menu, Integer> {

    /**
     * Lists all menu dtos.
     *
     * @param sort must not be null
     * @return a list of menu output dto
     */
    @NonNull
    List<MenuDTO> listDtos(@NonNull Sort sort);

    /**
     * Creates a menu.
     *
     * @param menuParam must not be null
     * @return created menu
     */
    @NonNull
    Menu createBy(@NonNull MenuParam menuParam);

    /**
     * Lists as menu tree.
     *
     * @param sort sort info must not be null
     * @return a menu tree
     */
    List<MenuVO> listAsTree(Sort sort);
}
