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
    public AuthorizationFilter authorizationFilter() {
        log.info("Authorization Filter Register.");
        return new AuthorizationFilter(jwtProvider);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        log.info("Exception Filter Register.");
        return new ExceptionHandlerFilter();
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistration(AuthorizationFilter authorizationFilter) {
        FilterRegistrationBean<AuthorizationFilter> registration = new FilterRegistrationBean<>(authorizationFilter);
        registration.addUrlPatterns("/api/v1/user/me/filter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilterRegistration(ExceptionHandlerFilter exceptionHandlerFilter) {
        FilterRegistrationBean<ExceptionHandlerFilter> registration = new FilterRegistrationBean<>(exceptionHandlerFilter);
        registration.addUrlPatterns("/api/v1/user/me/filter");
        registration.setOrder(0);
        return registration;
    }

}
