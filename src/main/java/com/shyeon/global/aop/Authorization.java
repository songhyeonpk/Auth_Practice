package com.shyeon.global.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

    // 이메일 값 바인딩 필요여부
    // 인가 과정은 필요하지만 이메일 바인딩 과정은 필요없다면 false
    boolean bindEmail() default true;
}
