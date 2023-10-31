package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthInfoResponse;
import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@SuperBuilder
public abstract class OAuthLoginResponseDto {
    private boolean isLoginSuccess;
    private OAuthProvider oAuthProvider;
    private String oAuthId;
    private String nickname;
}
