package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NaverAuthorizationDataResponseDto extends OAuthAuthorizationDataResponseDto {

    private String state;

    public static NaverAuthorizationDataResponseDto of(OAuthProvider oAuthProvider, OAuthRedirectDataDto data) {
        return NaverAuthorizationDataResponseDto.builder()
                .oAuthProvider(oAuthProvider)
                .authorizationCode(data.getCode())
                .state(data.getState())
                .build();
    }

}
