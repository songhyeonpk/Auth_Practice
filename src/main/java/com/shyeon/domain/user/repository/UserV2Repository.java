package com.shyeon.domain.user.repository;

import com.shyeon.domain.user.domain.UserV2;
import com.shyeon.global.oauth.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserV2Repository extends JpaRepository<UserV2, Long> {

    // SNS 정보로 회원 조회
    @Query(
            "select u from UserV2 u join u.snsUser s where s.oAuthProvider = :oAuthProvider and s.oAuthId = :oAuthId")
    UserV2 findUserBySnsUser(OAuthProvider oAuthProvider, String oAuthId);
}
