package com.shyeon.global.exception.customexception;

import com.shyeon.global.exception.errorcode.UserErrorCode;
import lombok.Getter;

@Getter
public class UserCustomException extends CustomException {

    public static final UserCustomException NOT_FOUND_USER =
            new UserCustomException(UserErrorCode.NOT_FOUND_USER);
    public static final UserCustomException ALREADY_EMAIL =
            new UserCustomException(UserErrorCode.ALREADY_EMAIL);
    public static final UserCustomException ALREADY_NICKNAME =
            new UserCustomException(UserErrorCode.ALREADY_NICKNAME);
    public static final UserCustomException INVALID_AUTHENTICATION =
            new UserCustomException(UserErrorCode.INVALID_AUTHENTICATION);

    public UserCustomException(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
