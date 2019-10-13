package com.lordz.lbt.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.lordz.lbt.auth.AuthInfo;
import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.model.params.LogParam;
import com.lordz.lbt.service.AdminService;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.DateUtils;
import com.lordz.lbt.utils.LBTUtils;
import com.lordz.lbt.utils.ServletUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.lordz.lbt.exception.BadRequestException;
import com.lordz.lbt.exception.NotFoundException;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.enums.LogType;
import com.lordz.lbt.model.params.UserParam;
import com.lordz.lbt.repository.UserRepository;
import com.lordz.lbt.service.base.AbstractCrudService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * UserService implementation class
 *
 * @author ryanwang
 * @date : 2019-03-14
 */
@Service
public class UserServiceImpl extends AbstractCrudService<User, Long> implements UserService {

    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    AdminService adminService;

    public UserServiceImpl(UserRepository userRepository ){
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getCurrentUser() {
        return ServletUtils.getCurrentRequest().map(this::getCurrentUser).orElse(null);
    }

    @Override
    public Optional<User> getCurrentUser(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return getCurrentUser(token);
    }

    @Override
    public Optional<User> getCurrentUser(String token) {
        AuthInfo authInfo = null;
        try {
            authInfo = adminService.verifyToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ExpiredJwtException) {
                throw new ForbiddenException("Token expired");
            }
        }
        if (authInfo == null || authInfo.getId() == null) {
            return Optional.empty();
        }
        return getByUsername(authInfo.getUsername());
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsernameOfNonNull(String username) {
        return getByUsername(username).orElseThrow(() -> new NotFoundException("The username dose not exist").setErrorData(username));
    }

    /**
     * Gets user by email.
     *
     * @param email email must not be blank
     * @return an optional user
     */
    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Gets non null user by email.
     *
     * @param email email
     * @return user info
     * @throws NotFoundException throws when the username does not exist
     */
    @Override
    public User getByEmailOfNonNull(String email) {
        return getByEmail(email).orElseThrow(() -> new NotFoundException("The email dose not exist").setErrorData(email));
    }

    @Override
    public User updatePassword(String oldPassword, String newPassword, Long userId) {
        Assert.hasText(oldPassword, "Old password must not be blank");
        Assert.hasText(newPassword, "New password must not be blank");
        Assert.notNull(userId, "User id must not be blank");

        if (oldPassword.equals(newPassword)) {
            throw new BadRequestException("新密码和旧密码不能相同");
        }

        // Get the user
        User user = getById(userId);

        // Check the user old password
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new BadRequestException("旧密码错误").setErrorData(oldPassword);
        }

        // Set new password
        setPassword(user, newPassword);

        // Update this user
        User updatedUser = update(user);

        // Log it
        eventPublisher.publishEvent(new LogParam(updatedUser.getId().toString(), LogType.PASSWORD_UPDATED, LBTUtils.desensitize(oldPassword, 2, 1)));

        return updatedUser;
    }

    @Override
    public User createBy(UserParam userParam) {
        Assert.notNull(userParam, "User param must not be null");

        User user = userParam.convertTo();

        setPassword(user, userParam.getPassword());

        return create(user);
    }

    @Override
    public void mustNotExpire(User user) {
        Assert.notNull(user, "User must not be null");

        Date now = DateUtils.now();
        if (user.getExpireTime() != null && user.getExpireTime().after(now)) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(user.getExpireTime().getTime() - now.getTime());
            // If expired
            throw new ForbiddenException("账号已被停用，请 " + LBTUtils.timeFormat(seconds) + " 后重试").setErrorData(seconds);
        }
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        Assert.notNull(user, "User must not be null");

        return !StringUtils.isBlank(plainPassword) && BCrypt.checkpw(plainPassword, user.getPassword());
    }

    @Override
    public User create(User user) {

        User createdUser = super.create(user);

        return createdUser;
    }

    @Override
    public User update(User user) {
        User updatedUser = super.update(user);

        // Log it
        eventPublisher.publishEvent(new LogParam( user.getId().toString(), LogType.PROFILE_UPDATED, user.getUsername()));

        return updatedUser;
    }

    @Override
    public void setPassword(@NonNull User user, @NonNull String plainPassword) {
        Assert.notNull(user, "User must not be null");
        Assert.hasText(plainPassword, "Plain password must not be blank");

        user.setPassword(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
    }

    @Override
    public void setDefaultAvatar(User user) {
        Assert.notNull(user, "User must not be null");
        StrBuilder gravatar = new StrBuilder("//cn.gravatar.com/avatar/");
        gravatar.append(SecureUtil.md5(user.getEmail()));
        gravatar.append("?s=256&d=mm");
        user.setAvatar(gravatar.toString());
    }
}
