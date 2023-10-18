package com.shyeon.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tokens {

    String accessToken;

    public static Tokens from(String accessToken) {
        return Tokens.builder()
                .accessToken(accessToken)
                .build();
    }
}
