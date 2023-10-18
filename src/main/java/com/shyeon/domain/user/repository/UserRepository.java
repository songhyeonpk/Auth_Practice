package com.shyeon.domain.user.repository;

import com.shyeon.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // email 중복확인
    boolean existsByEmail(String email);

    // nickname 중복확인
    boolean existsByNickname(String nickname);

    // 이메일로 회원조회
    Optional<User> findByEmail(String email);
}
