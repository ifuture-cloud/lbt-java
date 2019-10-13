package com.lordz.lbt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lordz.lbt.cache.InMemoryCacheStore;
import com.lordz.lbt.cache.StringCacheStore;
import com.lordz.lbt.filter.LogFilter;
import com.lordz.lbt.service.OptionService;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.HttpClientUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * LBT configuration.
 *
 */
@Configuration
@EnableConfigurationProperties(LBTProperties.class)
public class LBTConfiguration {

    private final static int TIMEOUT = 5000;

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.failOnEmptyBeans(false);
        return builder.build();
    }

    @Bean
    public RestTemplate httpsRestTemplate(RestTemplateBuilder builder) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        RestTemplate httpsRestTemplate = builder.build();
        httpsRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientUtils.createHttpsClient(TIMEOUT)));
        return httpsRestTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringCacheStore stringCacheStore() {
        return new InMemoryCacheStore();
    }


    /**
     * Creates a LogFilter.
     *
     * @return Log filter registration bean
     */
    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> logFilter = new FilterRegistrationBean<>();

        logFilter.setOrder(Ordered.HIGHEST_PRECEDENCE + 9);
        logFilter.setFilter(new LogFilter());
        logFilter.addUrlPatterns("/*");

        return logFilter;
    }

}
