package com.shyeon.domain.user.repository;

import com.shyeon.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 저장 테스트")
    void createUser() {
        /*
        given
         */
        User user = User.builder().email("ehftozl234@naver.com").password("thdgus!").nickname("헬로").build();

        /*
         when
         */
        User resultUser = userRepository.save(user);

        /*
         then
         */
        Assertions.assertThat(user.getEmail()).isEqualTo(resultUser.getEmail());
        Assertions.assertThat(user.getPassword()).isEqualTo(resultUser.getPassword());
        Assertions.assertThat(user.getNickname()).isEqualTo(resultUser.getNickname());
    }

    @Test
    @DisplayName("이메일, 닉네임 중복 테스트")
    void existEmail() {
        /*
        given
         */
        User user1 = User.builder().email("ehftozl234@naver.com").password("thdgus!").nickname("헬로").build();
        User user2 = User.builder().email("ehftozl234@naver.com").password("thdgus~").nickname("방망이").build();

        /*
        when
         */
        userRepository.save(user1);

        /*
        then
         */
        Assertions.assertThat(userRepository.existsByEmail(user2.getEmail())).isTrue();
        Assertions.assertThat(userRepository.existsByNickname(user2.getNickname())).isFalse();
    }

}