package com.shyeon.global.config;

import com.shyeon.global.interceptor.AuthorizationInterceptor;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(jwtProvider))
                .addPathPatterns("/api/v1/user/me/interceptor");
    }
}
