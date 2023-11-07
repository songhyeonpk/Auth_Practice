package com.shyeon.domain.oauth.dto;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.domain.UserV2;
import com.shyeon.domain.user.dto.Tokens;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

// OAuth 로그인 성공 응답정보 v2
@Getter
@SuperBuilder
public class OAuthLoginSuccessResponseDtoV2 extends OAuthLoginResponseDto {

    private Long id;
    private String email;
    private Tokens tokens;

    public static OAuthLoginSuccessResponseDtoV2 of(boolean isLoginSuccess, UserV2 user, Tokens tokens) {
        return OAuthLoginSuccessResponseDtoV2.builder()
                .isLoginSuccess(isLoginSuccess)
                .oAuthProvider(user.getSnsUser().getOAuthProvider())
                .oAuthId(user.getSnsUser().getOAuthId())
                .nickname(user.getNickname())
                .id(user.getId())
                .email(user.getEmail())
                .tokens(tokens)
                .build();
    }
}
