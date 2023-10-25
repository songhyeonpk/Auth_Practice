package com.shyeon.global.aop;

import com.shyeon.global.exception.customexception.CommonCustomException;
import com.shyeon.global.exception.customexception.TokenCustomException;
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

    @Around("@annotation(com.shyeon.global.aop.Authorization)")
    public Object authorization(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Authorization AOP Run.");

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Object[] modifiedArgs = joinPoint.getArgs();

        String accessToken = jwtProvider.resolveAccessToken(request);
        try {
            if(jwtProvider.validateToken(accessToken)) {
                String email = jwtProvider.parseClaims(accessToken).getSubject();
                modifiedArgs = modifyArgsWithEmail(joinPoint, email);
            }
        } catch (TokenCustomException e) {
            log.error("error_code : {}, message : {}", e.getErrorCode(), e.getErrorCode().getMessage());
            throw e;
        }

        return joinPoint.proceed(modifiedArgs);
    }

    // @Authorization 어노테이션이 붙어있는 메소드의 파라미터 값 조작
    private Object[] modifyArgsWithEmail(ProceedingJoinPoint joinPoint, String email) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();

        boolean parameterFound = false;
        for(int i = 0; i < parameterNames.length; i++) {
            if(parameterNames[i].equals("email")) {
                args[i] = email;
                parameterFound = true;
                break;
            }
        }

        if(!parameterFound) {
            throw CommonCustomException.DOES_NOT_EXIST_PARAMETER_IN_METHOD;
        }

        return args;
    }
}
