package com.lordz.lbt.service;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.dto.LinkDTO;
import com.lordz.lbt.model.entity.Link;
import com.lordz.lbt.model.params.LinkParam;
import com.lordz.lbt.model.vo.LinkTeamVO;
import com.lordz.lbt.service.base.CrudService;

import java.util.List;

/**
 * Link service.
 *
 *
 */
public interface LinkService extends CrudService<Link, Integer> {

    /**
     * List link dtos.
     *
     * @param sort sort
     * @return all links
     */
    @NonNull
    List<LinkDTO> listDtos(@NonNull Sort sort);

    /**
     * Lists link team vos.
     *
     * @param sort must not be null
     * @return a list of link team vo
     */
    @NonNull
    List<LinkTeamVO> listTeamVos(@NonNull Sort sort);

    /**
     * Creates link by link param.
     *
     * @param linkParam must not be null
     * @return create link
     */
    @NonNull
    Link createBy(@NonNull LinkParam linkParam);

    /**
     * Exists by link name.
     *
     * @param name must not be blank
     * @return true if exists; false otherwise
     */
    boolean existByName(String name);
}
