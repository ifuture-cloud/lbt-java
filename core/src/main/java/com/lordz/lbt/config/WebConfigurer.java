package com.lordz.lbt.config;

import com.lordz.lbt.service.UserService;
import com.lordz.lbt.support.AuthHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;

/**
 * @author ：zzz
 * @date ：Created in 7/18/19 4:35 PM
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    private static final String FILE_PROTOCOL = "file:///";

    @Autowired
    private UserService userService;
    @Autowired
    private LBTProperties lbtProperties;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthHandlerMethodArgumentResolver(userService));
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/"+USER_API_PREFIX+"/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workDir = FILE_PROTOCOL + lbtProperties.getWorkDir();
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations(workDir + "static/");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(workDir + "upload/");
        registry.addResourceHandler("/backup/**")
                .addResourceLocations(workDir + "backup/");

        if (!lbtProperties.isDocDisabled()) {
            // If doc is enable
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
