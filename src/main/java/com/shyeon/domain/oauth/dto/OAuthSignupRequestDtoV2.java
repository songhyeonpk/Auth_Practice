package com.shyeon.domain.oauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

// OAuth 회원가입 요청정보 v2
@Getter
public class OAuthSignupRequestDtoV2 {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞게 설정해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(
            regexp = "^[a-zA-Z]{6,}[^0-9a-zA-Zㄱ-ㅎ가-힣]{1}$",
            message = "비밀번호는 6자 이상의 영문으로만 조합하고 마지막에는 특수문자 1개를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "닉네임은 2~4자 이내의 한글이여야 합니다.")
    private String nickname;

    @NotBlank(message = "소셜 제공자 정보는 필수 값입니다.")
    private String oauthProvider;

    @NotBlank(message = "소셜 고유번호 정보는 필수 값입니다.")
    private String oauthId;
}
