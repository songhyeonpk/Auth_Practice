package com.shyeon.global.oauth;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    // OAuth 인증 서버로 Access Token 요청
    String requestAccessToken(OAuthLoginParams oAuthLoginParams);

    // OAuth 서버로 사용자 정보 요청
    OAuthInfoResponse requestOAuthUserInfo(String accessToken);
}
