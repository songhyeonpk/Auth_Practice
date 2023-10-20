package com.shyeon.global.exception.customexception;

import com.shyeon.global.exception.errorcode.TokenErrorCode;

public class TokenCustomException extends CustomException {

    public static final TokenCustomException DOES_NOT_EXIST_TOKEN = new TokenCustomException(TokenErrorCode.DOES_NOT_EXIST_TOKEN);
    public static final TokenCustomException EXPIRED_TOKEN = new TokenCustomException(TokenErrorCode.EXPIRED_TOKEN);
    public static final TokenCustomException WRONG_TYPE_SIGNATURE = new TokenCustomException(TokenErrorCode.WRONG_TYPE_SIGNATURE);
    public static final TokenCustomException WRONG_TYPE_TOKEN = new TokenCustomException(TokenErrorCode.WRONG_TYPE_TOKEN);
    public static final TokenCustomException MALFORMED_TOKEN = new TokenCustomException(TokenErrorCode.MALFORMED_TOKEN);

    public TokenCustomException(TokenErrorCode tokenErrorCode) {
        super(tokenErrorCode);
    }
}
