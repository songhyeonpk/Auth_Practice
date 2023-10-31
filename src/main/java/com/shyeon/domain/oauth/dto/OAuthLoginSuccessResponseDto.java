package com.shyeon.domain.oauth.dto;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.Tokens;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@SuperBuilder
public class OAuthLoginSuccessResponseDto extends OAuthLoginResponseDto {

    private Long id;
    private Tokens tokens;

    public static OAuthLoginSuccessResponseDto of (boolean isLoginSuccess, User user, Tokens tokens) {
        return OAuthLoginSuccessResponseDto.builder()
                .isLoginSuccess(isLoginSuccess)
                .oAuthProvider(user.getOAuthProvider())
                .oAuthId(user.getOAuthId())
                .nickname(user.getNickname())
                .id(user.getId())
                .tokens(tokens)
                .build();
    }
}
