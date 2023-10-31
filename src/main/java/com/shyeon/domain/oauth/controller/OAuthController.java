package com.shyeon.domain.oauth.controller;

import com.shyeon.domain.oauth.dto.OAuthLoginResponseDto;
import com.shyeon.domain.oauth.dto.OAuthSignupRequestDto;
import com.shyeon.domain.oauth.service.OAuthService;
import com.shyeon.global.oauth.kakao.KakaoLoginParams;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    // OAuth 회원가입 요청
    @PostMapping("/api/v1/{oAuthProvider}/user")
    public ResponseEntity<Long> oAuthRegister(
            @RequestBody @Valid OAuthSignupRequestDto signupRequest,
            @PathVariable String oAuthProvider) {
        System.out.println(oAuthProvider);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(oAuthService.oAuthRegister(signupRequest, oAuthProvider));
    }

    // 카카오 로그인 요청
    @PostMapping("/api/v1/kakao/login")
    public ResponseEntity<OAuthLoginResponseDto> kakaoLogin(
            @RequestBody KakaoLoginParams kakaoLoginParams) {
        return ResponseEntity.ok().body(oAuthService.oAuthLogin(kakaoLoginParams));
    }
}
