package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.*;
import com.shyeon.global.oauth.OAuthLoginParams;
import org.springframework.transaction.annotation.Transactional;

public interface OAuthService {

    // OAuth 서버로부터 받은 인가 데이터 응답
    OAuthAuthorizationDataResponseDto responseAuthData(String provider, OAuthRedirectDataDto data);

    // OAuth 로그인
    @Transactional
    OAuthLoginResponseDto oAuthLogin(OAuthLoginParams oAuthLoginParams);

    // OAuth 회원가입
    @Transactional
    Long oAuthRegister(OAuthSignupRequestDto signupRequest, String oAuthProvider);

    // OAuth 로그인 v2
    @Transactional
    OAuthLoginResponseDto oAuthLoginV2(OAuthLoginParams oAuthLoginParams);

    // OAuth 회원가입 v2
    @Transactional
    Long oAuthRegisterV2(OAuthSignupRequestDtoV2 signupRequest);
}
