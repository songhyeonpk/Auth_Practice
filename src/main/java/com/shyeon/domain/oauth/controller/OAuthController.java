package com.shyeon.domain.oauth.controller;

import com.shyeon.domain.oauth.dto.OAuthLoginResponseDto;
import com.shyeon.domain.oauth.dto.OAuthSignupRequestDto;
import com.shyeon.domain.oauth.service.OAuthService;
import com.shyeon.global.oauth.OAuthLoginParams;
import com.shyeon.global.oauth.google.GoogleLoginParams;
import com.shyeon.global.oauth.kakao.KakaoLoginParams;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<String> getRedirectUri(@PathVariable String provider, @RequestParam("code") String code) {
        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.responseOAuthCode(provider, code));
    }

    // 카카오 로그인 요청
    @PostMapping("/api/v1/kakao/login")
    public ResponseEntity<OAuthLoginResponseDto> kakaoLogin(
            @RequestBody KakaoLoginParams kakaoLoginParams) {
        return ResponseEntity.ok().body(oAuthService.oAuthLogin(kakaoLoginParams));
    }

    // 구글 로그인 요청
    @PostMapping("/api/v1/google/login")
    public ResponseEntity<OAuthLoginResponseDto> googleLogin(@RequestBody GoogleLoginParams googleLoginParams) {
        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.oAuthLogin(googleLoginParams));
    }
}
