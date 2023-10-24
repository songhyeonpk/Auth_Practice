package com.shyeon.global.interceptor;

import com.shyeon.global.exception.customexception.TokenCustomException;
import com.shyeon.global.util.jwt.JwtProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("AuthorizationInterceptor Run.");

        String accessToken = jwtProvider.resolveAccessToken(request);

        try {
            if (jwtProvider.validateToken(accessToken)) {
                String email = jwtProvider.parseClaims(accessToken).getSubject();
                request.setAttribute("email", email);
                return true;
            }
        } catch (TokenCustomException e) {
            log.error(
                    "code : {}, message : {}",
                    e.getErrorCode().getCode(),
                    e.getErrorCode().getMessage());
            throw e;
        }

        return false;
    }
}
