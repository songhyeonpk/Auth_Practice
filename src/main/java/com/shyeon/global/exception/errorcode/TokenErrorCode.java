package com.shyeon.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {

    DOES_NOT_EXIST_TOKEN(HttpStatus.UNAUTHORIZED, "T_001", "토큰이 존재하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "T_002", "만료된 토큰입니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "T_003", "잘못된 JWT 시그니처입니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "T_004", "지원되지 않는 형식이나 구성의 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "T_005", "손상된 토큰정보 입니다.");



    private HttpStatus status;
    private String code;
    private String message;

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.getCode();
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }
}
