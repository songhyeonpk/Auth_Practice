package com.shyeon.domain.user.repository;

import com.shyeon.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // email 중복확인
    boolean existsByEmail(String email);

    // nickname 중복확인
    boolean existsByNickname(String nickname);
}
