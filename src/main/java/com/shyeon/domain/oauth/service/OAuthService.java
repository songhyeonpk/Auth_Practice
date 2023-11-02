package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.OAuthLoginResponseDto;
import com.shyeon.domain.oauth.dto.OAuthSignupRequestDto;
import com.shyeon.global.oauth.OAuthLoginParams;

public interface OAuthService {

    // OAuth 서버로부터 받은 Code 응답
    String responseOAuthCode(String provider, String code);

    // OAuth 로그인
    OAuthLoginResponseDto oAuthLogin(OAuthLoginParams oAuthLoginParams);

    // OAuth 회원가입
    Long oAuthRegister(OAuthSignupRequestDto signupRequest, String oAuthProvider);
}
