package com.shyeon.domain.user.service;

import com.shyeon.domain.user.dto.SignupRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    // 회원가입
    @Transactional
    Long register(SignupRequestDto request);
}
