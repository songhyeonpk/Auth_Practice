package com.shyeon.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyeon.domain.user.dto.SignupRequestDto;
import com.shyeon.domain.user.service.UserService;
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
}
