package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class OAuthAuthorizationDataResponseDto {

    private OAuthProvider oAuthProvider;
    private String authorizationCode;

    public static OAuthAuthorizationDataResponseDto of(OAuthProvider oAuthProvider, String code) {
        return OAuthAuthorizationDataResponseDto.builder()
                .oAuthProvider(oAuthProvider)
                .authorizationCode(code)
                .build();
    }
}
