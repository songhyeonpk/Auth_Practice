package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.OAuthAuthorizationDataResponseDto;
import com.shyeon.domain.oauth.dto.OAuthLoginResponseDto;
import com.shyeon.domain.oauth.dto.OAuthRedirectDataDto;
import com.shyeon.domain.oauth.dto.OAuthSignupRequestDto;
import com.shyeon.global.oauth.OAuthLoginParams;

public interface OAuthService {

    // OAuth 서버로부터 받은 인가 데이터 응답
    OAuthAuthorizationDataResponseDto responseAuthData(String provider, OAuthRedirectDataDto data);

    // OAuth 로그인
    OAuthLoginResponseDto oAuthLogin(OAuthLoginParams oAuthLoginParams);

    // OAuth 회원가입
    Long oAuthRegister(OAuthSignupRequestDto signupRequest, String oAuthProvider);
}
