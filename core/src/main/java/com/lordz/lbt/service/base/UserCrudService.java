package com.lordz.lbt.service.base;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author ：zzz
 * @date ：Created in 8/6/19 4:16 PM
 */
public interface UserCrudService<DOMAIN, ID> extends CrudService<DOMAIN, ID> {


    /**
     * Find all by create user
     * @param userId
     * @param sort
     * @return
     */
    List<DOMAIN> findAllByCreateUser(@NonNull Long userId, @NonNull Sort sort);
}
