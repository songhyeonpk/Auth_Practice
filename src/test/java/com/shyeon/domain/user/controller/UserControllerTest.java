package com.shyeon.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.*;
import com.shyeon.domain.user.service.UserService;
import com.shyeon.global.util.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock private UserService userService;

    @Mock private JwtProvider jwtProvider;

    @InjectMocks private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원가입 API 테스트")
    void registerApiTest() throws Exception {
        // given
        SignupRequestDto request = new SignupRequestDto("ehftozl234@naver.com", "thdgus!", "헬로우");
        String jsonStr = objectMapper.writeValueAsString(request);

        // stub
        given(userService.register(any(SignupRequestDto.class))).willReturn(1L);

        // when & then
        mockMvc.perform(
                        post("/api/v1/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonStr))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 API 테스트")
    void loginApiTest() throws Exception {
        // given
        LoginRequestDto request = new LoginRequestDto("ehftozl234@naver.com", "thdgus!");
        String jsonStr = objectMapper.writeValueAsString(request);

        LoginResponseDto response =
                LoginResponseDto.of(1L, request.getEmail(), "헬로우", Tokens.from("access_token"));

        // stub
        given(userService.login(any(LoginRequestDto.class))).willReturn(response);

        // when & then
        mockMvc.perform(
                        post("/api/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonStr))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.email").value(request.getEmail()),
                        jsonPath("$.tokens.accessToken").value("access_token"))
                .andDo(print());
    }

    @Test
    @DisplayName("내 정보 조회 API")
    void myInfoTest() throws Exception {
        // given
        User user = User.builder().email("ehftozl234@naver.com").password("hashed_password").nickname("헬로우").build();
        UserInfoResponseDto response = UserInfoResponseDto.from(user);


        // stub
        given(jwtProvider.resolveAccessToken(any())).willReturn("access_token");
        given(userService.myInfo("access_token")).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/user/me")
                .header("Authorization", "Bearer access_token"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.email").value(user.getEmail()),
                        jsonPath("$.nickname").value(user.getNickname())
                )
                .andDo(print());
    }
}
