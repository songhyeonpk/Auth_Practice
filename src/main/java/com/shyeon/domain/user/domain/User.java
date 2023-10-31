package com.shyeon.domain.user.domain;

import com.shyeon.global.common.BaseTimeEntity;
import com.shyeon.global.oauth.OAuthProvider;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column private String email;

    @Column private String password;

    @Column private String nickname;

    @Column
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Column private String oAuthId;

    @Builder
    private User(
            String email,
            String password,
            String nickname,
            OAuthProvider oAuthProvider,
            String oAuthId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
        this.oAuthId = oAuthId;
    }
}
