package com.lordz.lbt.auth.filter;

import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.support.BaseResponse;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


/**
 * Auth Filter
 * @author ：zzz
 */
@Configuration
public class AuthRequestFilter extends OncePerRequestFilter {


    @Autowired
    UserService userService;

    private String[] ignorePath = {"/auth/","/users/register"};


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String httpMethod = request.getMethod();

        for (String url: ignorePath) {
            if (path.contains(url)) {
                filterChain.doFilter(request,response);
                return;
            }
        }



        if (!HttpMethod.GET.matches(httpMethod)) {
            try {
                Optional<User> user = userService.getCurrentUser(request);
                if (!user.isPresent()) {
                    //throw new ForbiddenException("Please login");
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write(JsonUtils.objectToJson(new BaseResponse<String>(403,"Forbidden.")));
                    return;
                }
            }catch (ForbiddenException e) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(JsonUtils.objectToJson(new BaseResponse<String>(403,e.getMessage())));
                return;
            }

        }

        filterChain.doFilter(request,response);
    }
}
