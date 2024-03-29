package com.lordz.lbt.service;

import com.lordz.lbt.exception.ForbiddenException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.lordz.lbt.exception.NotFoundException;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.params.UserParam;
import com.lordz.lbt.service.base.CrudService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * User service.
 *
 *
 */
public interface UserService extends CrudService<User, Long> {

    /**
     * Login failure count key.
     */
    String LOGIN_FAILURE_COUNT_KEY = "login.failure.count";

    /**
     * Max login try count.
     */
    int MAX_LOGIN_TRY = 5;

    /**
     * Lock minutes.
     */
    int LOCK_MINUTES = 10;

    /**
     * Gets current user.
     *
     * @return an optional user
     */
    @NonNull
    Optional<User> getCurrentUser(HttpServletRequest request);
    @NonNull
    Optional<User> getCurrentUser(String token);
    @NonNull
    Optional<User> getCurrentUser();

    /**
     * Gets user by username.
     *
     * @param username username must not be blank
     * @return an optional user
     */
    @NonNull
    Optional<User> getByUsername(@NonNull String username);

    /**
     * Gets non null user by username.
     *
     * @param username username
     * @return user info
     * @throws NotFoundException throws when the username does not exist
     */
    @NonNull
    User getByUsernameOfNonNull(@NonNull String username);

    /**
     * Gets user by email.
     *
     * @param email email must not be blank
     * @return an optional user
     */
    @NonNull
    Optional<User> getByEmail(@NonNull String email);

    /**
     * Gets non null user by email.
     *
     * @param email email
     * @return user info
     * @throws NotFoundException throws when the username does not exist
     */
    @NonNull
    User getByEmailOfNonNull(@NonNull String email);

    /**
     * Updates user password.
     *
     * @param oldPassword old password must not be blank
     * @param newPassword new password must not be blank
     * @param userId      user id must not be null
     * @return updated user detail
     */
    @NonNull
    User updatePassword(@NonNull String oldPassword, @NonNull String newPassword, @NonNull Long userId);

    /**
     * Creates an user.
     *
     * @param userParam user param must not be null.
     * @return created user
     */
    @NonNull
    User createBy(@NonNull UserParam userParam);

    /**
     * The user must not expire.
     *
     * @param user user info must not be null
     * @throws ForbiddenException throws if the given user has been expired
     */
    void mustNotExpire(@NonNull User user);

    /**
     * Checks the password is match the user password.
     *
     * @param user          user info must not be null
     * @param plainPassword plain password
     * @return true if the given password is match the user password; false otherwise
     */
    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);

    /**
     * Set user password.
     *
     * @param user          user must not be null
     * @param plainPassword plain password must not be blank
     */
    void setPassword(@NonNull User user, @NonNull String plainPassword);

    /**
     * Set user default avatar，use Gravatar(http://cn.gravatar.com)
     *
     * @param user user must not be null
     */
    void setDefaultAvatar(@NonNull User user);
}
