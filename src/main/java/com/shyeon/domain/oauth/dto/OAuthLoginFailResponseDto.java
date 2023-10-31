package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthInfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class OAuthLoginFailResponseDto extends OAuthLoginResponseDto {

    private String result;

    public static OAuthLoginFailResponseDto of(
            boolean isLoginSuccess, OAuthInfoResponse oAuthInfoResponse, String result) {
        return OAuthLoginFailResponseDto.builder()
                .isLoginSuccess(isLoginSuccess)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .oAuthId(oAuthInfoResponse.getOAuthId())
                .nickname(oAuthInfoResponse.getOAuthNickname())
                .result(result)
                .build();
    }
}
