package com.shyeon.domain.user.domain;

import com.shyeon.global.oauth.OAuthProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SnsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oAuthProvider;

    @Column(name = "oauth_id", unique = true)
    private String oAuthId;

    @Builder
    private SnsUser(OAuthProvider oAuthProvider, String oAuthId) {
        this.oAuthProvider = oAuthProvider;
        this.oAuthId = oAuthId;
    }
}
