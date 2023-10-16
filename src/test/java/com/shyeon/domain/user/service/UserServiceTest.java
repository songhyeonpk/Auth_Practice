package com.shyeon.domain.user.service;

import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.global.util.PasswordEncoder;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private SignupRequestDto request;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder();
        this.userService = new UserServiceImpl(userRepository, passwordEncoder);

        this.request = new SignupRequestDto("ehftozl234@naver.com", "thdgus!", "헬로우");
    }

    @Test
    @DisplayName("회원가입 시 비밀번호 암호화 테스트")
    void encryptPassword() {
        /*
        given
        */
        User user = createTestUserEntity();

        // mocking
        given(userRepository.save(any(User.class))).willReturn(user);

        /*
        when
         */
        userService.register(request);

        /*
        then
         */
        Assertions.assertThat(user.getPassword()).isEqualTo(passwordEncoder.encrypt(request.getEmail(), request.getPassword()));
    }

    @Test
    @DisplayName("회원 생성 성공 테스트")
    void successCreateUser() {
        /*
        given
         */
        User user = createTestUserEntity();
        ReflectionTestUtils.setField(user, "id", 1L);

        // mocking
        given(userRepository.save(any(User.class))).willReturn(user);

        /*
        when
         */
        Long userId = userService.register(request);

        /*
        then
         */
        Assertions.assertThat(user.getId()).isEqualTo(userId);
    }

    private User createTestUserEntity() {
        String encryptPassword = passwordEncoder.encrypt(request.getEmail(), request.getPassword());

        return User.builder()
                .email(request.getEmail())
                .password(encryptPassword)
                .nickname(request.getNickname())
                .build();
    }
}