package com.shyeon.domain.user.controller;

import com.shyeon.domain.user.dto.LoginRequestDto;
import com.shyeon.domain.user.dto.LoginResponseDto;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.dto.UserInfoResponseDto;
import com.shyeon.domain.user.service.UserService;
import com.shyeon.global.aop.Authorization;
import com.shyeon.global.util.jwt.JwtProvider;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 마이페이지 (내 정보 조회) 필터 적용
    @GetMapping("/api/v1/user/me/filter")
    public ResponseEntity<UserInfoResponseDto> myInfoWithFilter(HttpServletRequest request) {
        String email = String.valueOf(request.getAttribute("email"));
        return ResponseEntity.status(HttpStatus.OK).body(userService.myInfoWithFilter(email));
    }

    // 마이페이지 (내 정보 조회) 인터셉터 적용
    @GetMapping("/api/v1/user/me/interceptor")
    public ResponseEntity<UserInfoResponseDto> myInfoWithInterceptor(HttpServletRequest request) {
        String email = String.valueOf(request.getAttribute("email"));
        return ResponseEntity.status(HttpStatus.OK).body(userService.myInfoWithInterceptor(email));
    }

    // 마이페이지 (내 정보 조회) AOP 적용
    @GetMapping("/api/v1/user/me/aop")
    @Authorization
    public ResponseEntity<UserInfoResponseDto> myInfoWithAop(String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.myInfoWithAop(email));
    }

    // 회원 조회
    // 로그인한 회원에 한하여 다른 회원을 조회할 수 있으며, 로그인한 회원의 이메일 정보는 필요없는 요청
    @GetMapping("/api/v1/user/{userId}/aop")
    @Authorization(bindEmail = false)
    public ResponseEntity<UserInfoResponseDto> selectUserWithAop(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.selectUserWithAop(userId));
    }
}
