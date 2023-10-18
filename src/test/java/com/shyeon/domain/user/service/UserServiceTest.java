package com.shyeon.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.LoginRequestDto;
import com.shyeon.domain.user.dto.LoginResponseDto;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.global.util.PasswordEncoder;
import com.shyeon.global.util.jwt.JwtProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void successCreateUser() {
        // given
        SignupRequestDto request = new SignupRequestDto("ehftozl234@naver.com", "thdgus!", "헬로우");

        User user = User.builder().email(request.getEmail()).password("hashed_password").nickname(request.getNickname()).build();
        ReflectionTestUtils.setField(user, "id", 1L);

        // stub
        given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
        given(userRepository.existsByNickname(request.getNickname())).willReturn(false);
        given(passwordEncoder.encrypt(request.getEmail(), request.getPassword())).willReturn("hashed_password");
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        Long userId = userService.register(request);


        // then
        Assertions.assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void successLogin() {
        // given
        LoginRequestDto request = new LoginRequestDto("ehftozl234@naver.com", "thdgus!");

        User user = User.builder().email(request.getEmail()).password("hashed_password").build();
        ReflectionTestUtils.setField(user, "id", 1L);

        // stub
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.encrypt(request.getEmail(), request.getPassword())).willReturn("hashed_password");
        given(jwtProvider.generateAccessToken(request.getEmail())).willReturn("access_token");

        // when
        LoginResponseDto response = userService.login(request);

        // then
        Assertions.assertThat(response.getId()).isEqualTo(user.getId());
        Assertions.assertThat(response.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(response.getTokens().getAccessToken()).isEqualTo("access_token");
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void failLogin() {
        // given
        LoginRequestDto request = new LoginRequestDto("ehftozl234@naver.com", "thdgus!");

        User user = User.builder().email("ehftozl234@naver.com").password("hashed_password").build();

        // stub
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.encrypt(request.getEmail(), request.getPassword())).willReturn("fake_password");
        given(jwtProvider.generateAccessToken(request.getEmail())).willReturn("access_token");

        // when
        LoginResponseDto response = userService.login(request);
    }
}
