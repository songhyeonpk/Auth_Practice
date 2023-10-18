package com.shyeon.domain.user.controller;

import com.shyeon.domain.user.dto.LoginRequestDto;
import com.shyeon.domain.user.dto.LoginResponseDto;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}
