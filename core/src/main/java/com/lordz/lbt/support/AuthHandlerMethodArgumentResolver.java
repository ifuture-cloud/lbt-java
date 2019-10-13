package com.lordz.lbt.support;

import com.lordz.lbt.auth.annotation.Auth;
import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

/**
 * @author ：zzz
 * @date ：Created in 7/18/19 4:38 PM
 */
public class AuthHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    UserService userService;

    public AuthHandlerMethodArgumentResolver(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        Optional<User> user = userService.getCurrentUser(webRequest.getHeader(HttpHeaders.AUTHORIZATION));
        if (user.isPresent()) {
            return user.get();
        }
        throw new ForbiddenException("Login?");
    }
}
