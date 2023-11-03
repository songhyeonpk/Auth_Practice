package com.shyeon.domain.oauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthRedirectDataDto {

    private String code;

    private String state;
}
