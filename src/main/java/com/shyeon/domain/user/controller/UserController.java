package com.shyeon.domain.user.controller;

import com.shyeon.domain.user.dto.LoginRequestDto;
import com.shyeon.domain.user.dto.LoginResponseDto;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.dto.UserInfoResponseDto;
import com.shyeon.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    // 회원가입
    @PostMapping("/api/v1/user")
    public ResponseEntity<Long> register(@RequestBody @Valid SignupRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    // 로그인
    @PostMapping("/api/v1/user/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }

    // 마이페이지 (내 정보 조회)
    @GetMapping("/api/v1/user/me")
    public ResponseEntity<UserInfoResponseDto> myInfo(HttpServletRequest request) {
        String accessToken = jwtProvider.resolveAccessToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(userService.myInfo(accessToken));
    }
}
