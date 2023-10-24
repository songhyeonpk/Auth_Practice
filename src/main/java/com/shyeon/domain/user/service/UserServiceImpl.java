package com.shyeon.domain.user.service;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.*;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.global.exception.customexception.UserCustomException;
import com.shyeon.global.util.PasswordEncoder;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 회원가입
    @Override
    public Long register(SignupRequestDto request) {
        // 이메일 검사
        existEmail(request.getEmail());

        // 닉네임 검사
        existNickname(request.getNickname());

        // 비밀번호 암호화
        String encryptionPassword =
                passwordEncoder.encrypt(request.getEmail(), request.getPassword());

        User user =
                User.builder()
                        .email(request.getEmail())
                        .password(encryptionPassword)
                        .nickname(request.getNickname())
                        .build();

        return userRepository.save(user).getId();
    }

    // email 중복확인
    private void existEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw UserCustomException.ALREADY_EMAIL;
        }
    }

    // 닉네임 중복확인
    private void existNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw UserCustomException.ALREADY_NICKNAME;
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        // 회원 조회
        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        // 회원 인증
        String encryptPassword = passwordEncoder.encrypt(request.getEmail(), request.getPassword());
        if (!user.getPassword().equals(encryptPassword)) {
            throw UserCustomException.INVALID_AUTHENTICATION;
        }

        // AccessToken 발급
        String accessToken = jwtProvider.generateAccessToken(request.getEmail());

        return LoginResponseDto.of(
                user.getId(), user.getEmail(), user.getNickname(), Tokens.from(accessToken));
    }

    @Override
    public UserInfoResponseDto myInfo(String accessToken) {
        // Access Token 검증
        jwtProvider.validateToken(accessToken);

        // 토큰에 저장된 회원 이메일 정보 가져오기
        String email = jwtProvider.parseClaims(accessToken).getSubject();

        // 회원 조회
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        return UserInfoResponseDto.from(user);
    }

    @Override
    public UserInfoResponseDto myInfoWithFilter(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        return UserInfoResponseDto.from(user);
    }

    @Override
    public UserInfoResponseDto myInfoWithInterceptor(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        return UserInfoResponseDto.from(user);
    }
}
