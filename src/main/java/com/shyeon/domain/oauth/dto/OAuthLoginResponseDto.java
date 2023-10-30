package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@SuperBuilder
public class OAuthLoginResponseDto {
    private OAuthProvider oAuthProvider;
    private String oAuthId;
    private String nickname;
}
