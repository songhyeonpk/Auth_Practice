package com.shyeon.domain.user.repository;

import com.shyeon.domain.user.domain.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {}
