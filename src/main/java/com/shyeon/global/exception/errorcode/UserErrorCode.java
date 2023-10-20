package com.shyeon.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, "U_001", "해당 회원이 존재하지 않습니다."),
    ALREADY_EMAIL(HttpStatus.CONFLICT, "U_002", "이미 사용중인 이메일입니다."),
    ALREADY_NICKNAME(HttpStatus.CONFLICT, "U_003", "이미 사용중인 닉네임입니다."),
    INVALID_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "U_004", "회원 인증에 실패하였습니다.");


    private HttpStatus status;
    private String code;
    private String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
