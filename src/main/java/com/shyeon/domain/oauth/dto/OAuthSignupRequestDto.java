package com.shyeon.domain.oauth.dto;

import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class OAuthSignupRequestDto {

    @NotBlank(message = "oAuthId 값은 필수 값입니다.")
    private String oauthId;

    @NotBlank(message = "닉네임 값은 필수 입력 값입니다.")
    @Size(min = 2, max = 4, message = "닉네임은 2~4자 이내로 설정해야 합니다.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "닉네임은 2~4자 이내의 한글이여야 합니다.")
    private String nickname;
}
