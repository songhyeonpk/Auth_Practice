package com.shyeon.domain.user.service;

import com.shyeon.domain.user.dto.LoginRequestDto;
import com.shyeon.domain.user.dto.LoginResponseDto;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.dto.UserInfoResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    // 회원가입
    @Transactional
    Long register(SignupRequestDto request);

    // 로그인
    @Transactional
    LoginResponseDto login(LoginRequestDto request);

    // 내 정보 조회
    @Transactional(readOnly = true)
    UserInfoResponseDto myInfo(String accessToken);

    // 내 정보 조회, 필터
    @Transactional(readOnly = true)
    UserInfoResponseDto myInfoWithFilter(String email);
}
