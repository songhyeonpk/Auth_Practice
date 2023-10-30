package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.OAuthLoginResponseDto;
import com.shyeon.global.oauth.OAuthLoginParams;

public interface OAuthService {

    // OAuth 로그인
    OAuthLoginResponseDto oAuthLogin(OAuthLoginParams oAuthLoginParams);
}
