package com.lordz.lbt.service;

import org.springframework.data.domain.Page;
import com.lordz.lbt.model.dto.LogDTO;
import com.lordz.lbt.model.entity.Log;
import com.lordz.lbt.service.base.CrudService;

/**
 * Log service.
 *
 *
 */
public interface LogService extends CrudService<Log, Long> {

    /**
     * Lists latest logs.
     *
     * @param top top number must not be less than 0
     * @return a page of latest logs
     */
    Page<LogDTO> pageLatest(int top);
}
