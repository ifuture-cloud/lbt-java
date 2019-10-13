package com.lordz.lbt.repository.base;

import com.sun.mail.imap.protocol.ID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author ：zzz
 */
@NoRepositoryBean
public interface UserBaseRepository<DOMAIN, ID>  extends  BaseRepository<DOMAIN, ID> {


    /**
     * Find all by create user
     * @param userId
     * @param sort
     * @return
     */
    List<DOMAIN> findAllByCreateUser(@NonNull Long userId, @NonNull Sort sort);
}
