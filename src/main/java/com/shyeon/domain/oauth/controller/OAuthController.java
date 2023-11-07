package com.shyeon.domain.oauth.controller;

import com.shyeon.domain.oauth.dto.*;
import com.shyeon.domain.oauth.service.OAuthService;
import com.shyeon.global.oauth.google.GoogleLoginParams;
import com.shyeon.global.oauth.kakao.KakaoLoginParams;
import com.shyeon.global.oauth.naver.NaverLoginParams;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 소셜 서비스 Redirect URI
    @GetMapping("/{provider}/callback")
    public ResponseEntity<OAuthAuthorizationDataResponseDto> getRedirectUri(
            @PathVariable String provider, OAuthRedirectDataDto data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(oAuthService.responseAuthData(provider, data));
    }

    // 카카오 로그인 요청
    @PostMapping("/api/v1/kakao/login")
    public ResponseEntity<OAuthLoginResponseDto> kakaoLogin(
            @RequestBody KakaoLoginParams kakaoLoginParams) {
        return ResponseEntity.ok().body(oAuthService.oAuthLogin(kakaoLoginParams));
    }

    // 구글 로그인 요청
    @PostMapping("/api/v1/google/login")
    public ResponseEntity<OAuthLoginResponseDto> googleLogin(
            @RequestBody GoogleLoginParams googleLoginParams) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(oAuthService.oAuthLogin(googleLoginParams));
    }

    // 네이버 로그인 요청
    @PostMapping("/api/v1/naver/login")
    public ResponseEntity<OAuthLoginResponseDto> naverLogin(
            @RequestBody NaverLoginParams naverLoginParams) {
        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.oAuthLogin(naverLoginParams));
    }

    // OAuth 회원가입 요청 v2
    @PostMapping("/api/v1/oauth/user_v2")
    public ResponseEntity<Long> oAuthRegisterV2(
            @RequestBody @Valid OAuthSignupRequestDtoV2 signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(oAuthService.oAuthRegisterV2(signupRequest));
    }

    // 카카오 로그인 요청 v2
    @PostMapping("/api/v1/kakao/login_v2")
    public ResponseEntity<OAuthLoginResponseDto> kakaoLoginV2(
            @RequestBody KakaoLoginParams kakaoLoginParams) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(oAuthService.oAuthLoginV2(kakaoLoginParams));
    }
}
