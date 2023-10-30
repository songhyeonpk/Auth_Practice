package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthInfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@SuperBuilder
public class OAuthLoginFailResponseDto extends OAuthLoginResponseDto {

    private String result;

    public static OAuthLoginFailResponseDto of (OAuthInfoResponse oAuthInfoResponse, String result) {
        return OAuthLoginFailResponseDto.builder()
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .oAuthId(oAuthInfoResponse.getOAuthId())
                .nickname(oAuthInfoResponse.getOAuthNickname())
                .result(result)
                .build();
    }
}