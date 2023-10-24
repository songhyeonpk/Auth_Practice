package com.shyeon.global.config;

import com.shyeon.global.filter.AuthorizationFilter;
import com.shyeon.global.filter.ExceptionHandlerFilter;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistration() {
        log.info("Authorization Filter Register.");

        FilterRegistrationBean<AuthorizationFilter> registration =
                new FilterRegistrationBean<>(new AuthorizationFilter(jwtProvider));
        registration.addUrlPatterns("/api/v1/user/me/filter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilterRegistration() {
        log.info("Exception Filter Register.");

        FilterRegistrationBean<ExceptionHandlerFilter> registration =
                new FilterRegistrationBean<>(new ExceptionHandlerFilter());
        registration.addUrlPatterns("/api/v1/user/me/filter");
        registration.setOrder(0);
        return registration;
    }
}
