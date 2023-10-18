package com.shyeon.domain.user.service;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.LoginRequestDto;
import com.shyeon.domain.user.dto.LoginResponseDto;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.dto.Tokens;
import com.shyeon.domain.user.repository.UserRepository;
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
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }
    }

    // 닉네임 중복확인
    private void existNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        // 회원 조회
        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        // 회원 인증
        String encryptPassword = passwordEncoder.encrypt(request.getEmail(), request.getPassword());
        if (!user.getPassword().equals(encryptPassword)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }

        // AccessToken 발급
        String accessToken = jwtProvider.generateAccessToken(request.getEmail());

        return LoginResponseDto.of(
                user.getId(), user.getEmail(), user.getNickname(), Tokens.from(accessToken));
    }
}
