package com.lordz.lbt.service;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Recovery service interface.
 *
 *
 * @date 19-4-26
 */
public interface RecoveryService {

    /**
     * Migrates from halo version 0.4.3.
     *
     * @param file multipart file must not be null
     */
    void migrateFromV0_4_3(@NonNull MultipartFile file);
}
