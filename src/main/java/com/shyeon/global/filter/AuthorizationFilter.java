package com.shyeon.global.filter;

import com.shyeon.global.util.jwt.JwtProvider;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("AuthorizationFilter Run.");

        // request header 에서 access token 추출
        String accessToken = jwtProvider.resolveAccessToken(request);

        // access token 검증 및 access token 에서 회원 이메일 값 추출
        if (jwtProvider.validateToken(accessToken)) {
            String email = extractUserEmailFromToken(accessToken);
            request.setAttribute("email", email);
        }

        filterChain.doFilter(request, response);
    }

    // Token 에서 회원 이메일 추출
    private String extractUserEmailFromToken(String token) {
        return jwtProvider.parseClaims(token).getSubject();
    }
}
