package com.shyeon.global.exception.customexception;

import com.shyeon.global.exception.errorcode.OAuthErrorCode;

public class OAuthCustomException extends CustomException {

    public static final OAuthCustomException UNSUPPORTED_OAUTH_LOGIN = new OAuthCustomException(OAuthErrorCode.UNSUPPORTED_OAUTH_LOGIN);

    public OAuthCustomException(OAuthErrorCode oAuthErrorCode) {
        super(oAuthErrorCode);
    }
}
