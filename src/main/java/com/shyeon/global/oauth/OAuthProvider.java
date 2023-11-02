package com.shyeon.global.oauth;

import com.shyeon.global.exception.customexception.OAuthCustomException;
import java.util.Arrays;

public enum OAuthProvider {
    KAKAO,
    GOOGLE;

    // OAuthProvider 값으로 변환하고, 일치하는 Provider 가 없는 경우 Exception 처리
    public static OAuthProvider convert(String oAuthProvider) {
        return Arrays.stream(OAuthProvider.values())
                .filter(provider -> provider.toString().equals(oAuthProvider.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> OAuthCustomException.UNSUPPORTED_OAUTH_LOGIN);
    }
}
