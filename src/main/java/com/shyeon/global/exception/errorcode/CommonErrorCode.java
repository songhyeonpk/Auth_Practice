package com.shyeon.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "C_001", "잘못된 형식의 데이터입니다."),
    DOES_NOT_EXIST_PARAMETER_IN_METHOD(
            HttpStatus.BAD_REQUEST, "C_002", "바인딩될 파라미터가 해당 메소드에 존재하지 않습니다. 메소드의 파라미터를 확인해주세요,");

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
