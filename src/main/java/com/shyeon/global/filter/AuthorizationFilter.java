package com.shyeon.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyeon.global.exception.ErrorResponse;
import com.shyeon.global.exception.customexception.TokenCustomException;
import com.shyeon.global.exception.errorcode.ErrorCode;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // request header 에서 access token 추출
        String accessToken = jwtProvider.resolveAccessToken(request);

        // access token 검증 및 access token 에서 회원 이메일 값 추출
        if(jwtProvider.validateToken(accessToken)) {
            String email = extractUserEmailFromToken(accessToken);
            request.setAttribute("email", email);
        }

        filterChain.doFilter(request, response);
    }

    // Token 에서 회원 이메일 추출
    private String extractUserEmailFromToken (String token) {
        return jwtProvider.parseClaims(token).getSubject();
    }
}
