package com.lordz.lbt.support;

import com.lordz.lbt.auth.annotation.Role;
import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zzz
 * @date ：Created in 7/24/19 3:11 PM
 */
@Slf4j
@Aspect
@Configuration
public class RoleInterceptor {

    @Autowired
    private UserService userService;


    @Around("@annotation(com.lordz.lbt.auth.annotation.Role)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Role role = methodSignature.getMethod().getAnnotation(Role.class);

        User user = userService.getCurrentUser().orElseThrow(() -> new ForbiddenException("forbidden"));
        if (role.value().equals(user.getRole())){
            return joinPoint.proceed();
        }
        throw new ForbiddenException("forbidden");
    }
}
