package com.shyeon.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    UNSUPPORTED_OAUTH_LOGIN(HttpStatus.BAD_REQUEST, "O_001", "지원하지 않는 소셜 로그인입니다.");

    private HttpStatus status;
    private String code;
    private String message;

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
