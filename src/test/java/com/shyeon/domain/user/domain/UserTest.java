package com.shyeon.domain.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("회원 객체가 생성되는지 확인하는 테스트")
    void createUser() {
        /*
        given
         */
        User user =
                User.builder()
                        .email("ehftozl234@naver.com")
                        .password("thdgus!")
                        .nickname("송현")
                        .build();

        /*
        when, then
         */
        Assertions.assertThat(user.getEmail()).isEqualTo("ehftozl234@naver.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("thdgus!");
        Assertions.assertThat(user.getNickname()).isEqualTo("송현");
    }
}
