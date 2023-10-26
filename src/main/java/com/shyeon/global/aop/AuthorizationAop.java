package com.shyeon.global.aop;

import com.shyeon.global.exception.customexception.CommonCustomException;
import com.shyeon.global.exception.customexception.TokenCustomException;
import com.shyeon.global.util.DataBinder;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationAop {

    private final JwtProvider jwtProvider;
    private final DataBinder dataBinder;

    @Around("@annotation(com.shyeon.global.aop.Authorization)")
    public Object authorization(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Authorization AOP Run.");

        // 동작 메소드에 포함된 Authorization 객체 정보 가져오기
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorization auth = method.getAnnotation(Authorization.class);

        // Request 정보에서 Access Token 값 추출
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String accessToken = jwtProvider.resolveAccessToken(request);
        try {
            log.info(String.valueOf(auth.bindEmail()));

            /*
             * Access Token 검증 및 바인딩 여부 확인
             * 토큰이 유효하지 않으면 예외처리
             * 토큰이 유효하고 이메일 데이터가 필요한 요청이면 파라미터 바인딩 로직 수행
             * 토큰이 유효하고 이메일 데이터가 필요하지 않은 요청이면 바로 컨트롤러 요청 동작
             */
            if(jwtProvider.validateToken(accessToken) && auth.bindEmail()) {
                String email = jwtProvider.parseClaims(accessToken).getSubject();
                Object[] modifiedArgs = dataBinder.emailBind(joinPoint.getArgs(), signature, email);

                return joinPoint.proceed(modifiedArgs);
            }

            return joinPoint.proceed();
        } catch (TokenCustomException e) {
            log.error("error_code : {}, message : {}", e.getErrorCode(), e.getErrorCode().getMessage());
            throw e;
        }
    }
}
