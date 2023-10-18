package com.shyeon.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private Tokens tokens;

    public static LoginResponseDto of(Long id, String email, String nickname, Tokens tokens) {
        return LoginResponseDto.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .tokens(tokens)
                .build();
    }
}
