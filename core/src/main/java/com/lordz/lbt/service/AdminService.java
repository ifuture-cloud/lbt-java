package com.lordz.lbt.service;

import com.lordz.lbt.auth.AuthInfo;
import com.lordz.lbt.auth.Authorized;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.dto.EnvironmentDTO;
import com.lordz.lbt.model.dto.StatisticDTO;
import com.lordz.lbt.model.params.LoginParam;

/**
 * Admin service.
 *
 *
 * @author ryanwang
 * @date 19-4-29
 */
public interface AdminService {


    /**
     * Authenticates.
     *
     * @param loginParam login param must not be null
     * @return authentication token
     */
    @NonNull
    Authorized authenticate(@NonNull LoginParam loginParam);


    /**
     * Get system counts.
     *
     * @return count dto
     */
    @NonNull
    StatisticDTO getCount();

    /**
     * Get system environments
     *
     * @return environments
     */
    @NonNull
    EnvironmentDTO getEnvironments();

    /**
     * Refreshes token.
     *
     * @param refreshToken refresh token must not be blank
     * @return authentication token
     */
    @NonNull
    Authorized refreshToken(@NonNull String refreshToken);


    AuthInfo verifyToken(@NonNull String token) throws Exception;

    /**
     * Updates halo admin assets.
     */
    void updateAdminAssets();
}
