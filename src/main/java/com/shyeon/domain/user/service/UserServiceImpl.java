package com.shyeon.domain.user.service;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.global.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public Long register(SignupRequestDto request) {
        // 이메일 검사
        existEmail(request.getEmail());

        // 닉네임 검사
        existNickname(request.getNickname());

        // 비밀번호 암호화
        String encryptionPassword = passwordEncoder.encrypt(request.getEmail(), request.getPassword());

        User user = User.builder().email(request.getEmail()).password(encryptionPassword).nickname(request.getNickname())
                .build();

        return userRepository.save(user).getId();
    }

    // email 중복확인
    private void existEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }
    }

    // 닉네임 중복확인
    private void existNickname(String nickname) {
        if(userRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }
    }
}
