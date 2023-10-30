package com.shyeon.domain.oauth.controller;

import com.shyeon.domain.oauth.dto.OAuthLoginResponseDto;
import com.shyeon.domain.oauth.service.OAuthService;
import com.shyeon.global.oauth.kakao.KakaoLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    // 카카오 로그인 요청
    @PostMapping("/api/v1/kakao/login")
    public ResponseEntity<OAuthLoginResponseDto> kakaoLogin(@RequestBody KakaoLoginParams kakaoLoginParams) {
        return ResponseEntity.ok().body(oAuthService.oAuthLogin(kakaoLoginParams));
    }
}
