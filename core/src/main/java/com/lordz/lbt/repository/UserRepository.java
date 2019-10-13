package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BaseRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.entity.User;

import java.util.Optional;

/**
 * User repository.
 *
 *
 */
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * Gets user by username.
     *
     * @param username username must not be blank
     * @return an optional user
     */
    @NonNull
    Optional<User> findByUsername(@NonNull String username);

    /**
     * Gets user by email.
     *
     * @param email email must not be blank
     * @return an optional user
     */
    @NonNull
    Optional<User> findByEmail(@NonNull String email);
}
