package com.shyeon.global.util;

import com.shyeon.global.exception.customexception.CommonCustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

// 데이터를 파라미터로 바인딩 해주는 객체
@Component
@Slf4j
public class DataBinder {

    // 이메일 값을 파라미터로 바인딩하는 메소드
    public Object[] emailBind(Object[] args, MethodSignature signature, String email) {
        log.info("Email Bind Run.");

        String[] parameterNames = signature.getParameterNames();
        boolean bindEmail = false;

        for(int i = 0; i < parameterNames.length; i++) {
            if(parameterNames[i].equals("email")) {
                args[i] = email;
                bindEmail = true;
                break;
            }
        }

        if(!bindEmail) {
            throw CommonCustomException.DOES_NOT_EXIST_PARAMETER_IN_METHOD;
        }

        return args;
    }
}
