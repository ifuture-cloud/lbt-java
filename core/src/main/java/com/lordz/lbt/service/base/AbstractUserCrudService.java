package com.lordz.lbt.service.base;

import com.lordz.lbt.repository.base.BaseRepository;
import com.lordz.lbt.repository.base.UserBaseRepository;
import com.sun.mail.imap.protocol.ID;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author ：zzz
 * @date ：Created in 8/6/19 4:17 PM
 */
public abstract class AbstractUserCrudService<DOMAIN, ID> extends AbstractCrudService<DOMAIN, ID> implements UserCrudService<DOMAIN, ID>{


    private final UserBaseRepository<DOMAIN, ID> repository;

    protected AbstractUserCrudService(UserBaseRepository<DOMAIN, ID> repository) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Find all by create user
     * @param userId
     * @param sort
     * @return
     */
    @Override
    public List<DOMAIN> findAllByCreateUser(Long userId, Sort sort) {
        return this.repository.findAllByCreateUser(userId,sort);
    }
}
