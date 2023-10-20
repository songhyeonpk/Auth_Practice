package com.shyeon.global.exception;

import com.shyeon.global.exception.errorcode.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {

    private HttpStatus status;
    private String code;
    private String message;
    private List<CustomFieldError> errors;

    @Getter
    @AllArgsConstructor
    static class CustomFieldError {

        private String field;
        private String invalidValue;
        private String reason;

        private CustomFieldError(FieldError fieldError) {
            this.field = fieldError.getField();
            this.invalidValue = fieldError.getRejectedValue().toString();
            this.reason = fieldError.getDefaultMessage();
        }
    }

    private void setError(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private ErrorResponse(ErrorCode errorCode) {
        setError(errorCode);
    }

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        setError(errorCode);
        this.errors = errors.stream().map(CustomFieldError::new).collect(Collectors.toList());
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, bindingResult.getFieldErrors());
    }
}
