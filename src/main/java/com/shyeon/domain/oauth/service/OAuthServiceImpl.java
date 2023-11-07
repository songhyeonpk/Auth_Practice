package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.*;
import com.shyeon.domain.user.domain.SnsUser;
import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.domain.UserV2;
import com.shyeon.domain.user.dto.Tokens;
import com.shyeon.domain.user.repository.SnsUserRepository;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.domain.user.repository.UserV2Repository;
import com.shyeon.global.exception.customexception.UserCustomException;
import com.shyeon.global.oauth.*;
import com.shyeon.global.util.PasswordEncoder;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final RequestOAuthApiClientService requestOAuthApiClientService;
    private final UserRepository userRepository;
    private final UserV2Repository userV2Repository;
    private final SnsUserRepository snsUserRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuthAuthorizationDataResponseDto responseAuthData(
            String provider, OAuthRedirectDataDto data) {
        OAuthProvider oAuthProvider = OAuthProvider.convert(provider);

        if (oAuthProvider == OAuthProvider.NAVER) {
            return NaverAuthorizationDataResponseDto.of(oAuthProvider, data);
        }

        return OAuthAuthorizationDataResponseDto.of(oAuthProvider, data.getCode());
    }

    @Override
    public OAuthLoginResponseDto oAuthLogin(OAuthLoginParams oAuthLoginParams) {
        OAuthInfoResponse response = requestOAuthApiClientService.requestOAuth(oAuthLoginParams);

        return responseOAuthLogin(response);
    }

    private OAuthLoginResponseDto responseOAuthLogin(OAuthInfoResponse oAuthInfoResponse) {
        User user =
                userRepository
                        .findByEmail(
                                oAuthInfoResponse.getOAuthProvider()
                                        + "_"
                                        + oAuthInfoResponse.getOAuthId())
                        .orElse(null);

        if (user != null) {
            String accessToken = jwtProvider.generateAccessToken(user.getEmail());

            return OAuthLoginSuccessResponseDto.of(true, user, Tokens.from(accessToken));
        }

        return OAuthLoginFailResponseDto.of(false, oAuthInfoResponse, "소셜 간편 회원가입이 필요합니다.");
    }

    @Override
    public Long oAuthRegister(OAuthSignupRequestDto signupRequest, String oAuthProvider) {
        // OAuthProvider 변환, 지원하지 않는 소셜 로그인일 시 예외처리
        OAuthProvider provider = OAuthProvider.convert(oAuthProvider);

        // 닉네임 중복확인
        existsNickname(signupRequest.getNickname());

        User user =
                User.builder()
                        .email(provider + "_" + signupRequest.getOauthId())
                        .nickname(signupRequest.getNickname())
                        .oAuthProvider(provider)
                        .oAuthId(signupRequest.getOauthId())
                        .build();

        return userRepository.save(user).getId();
    }

    @Override
    public OAuthLoginResponseDto oAuthLoginV2(OAuthLoginParams oAuthLoginParams) {
        OAuthInfoResponse response = requestOAuthApiClientService.requestOAuth(oAuthLoginParams);

        return responseOAuthLoginV2(response);
    }

    private OAuthLoginResponseDto responseOAuthLoginV2(OAuthInfoResponse response) {
        UserV2 user = userV2Repository.findUserBySnsUser(response.getOAuthProvider(), response.getOAuthId());

        // 로그인 진행, JWT 발급
        if(user != null) {
            String accessToken = jwtProvider.generateAccessToken(user.getEmail());

            return OAuthLoginSuccessResponseDtoV2.of(true, user, Tokens.from(accessToken));
        }

        return OAuthLoginFailResponseDto.of(false, response, "소셜 간편 회원가입이 필요합니다.");
    }

    @Override
    public Long oAuthRegisterV2(OAuthSignupRequestDtoV2 signupRequest) {
        // 이메일 중복확인
        existsEmail(signupRequest.getEmail());

        // 닉네임 중복확인
        existsNickname(signupRequest.getNickname());

        // 소셜 제공자 검증 및 타입 변환
        OAuthProvider provider = OAuthProvider.convert(signupRequest.getOauthProvider());

        // SnsUser 생성
        SnsUser snsUser = SnsUser.builder()
                .oAuthProvider(provider)
                .oAuthId(signupRequest.getOauthId())
                .build();

        // SnsUser 저장
        snsUserRepository.save(snsUser);

        // 비밀번호 암호화
        String encryptPassword = passwordEncoder.encrypt(signupRequest.getEmail(), signupRequest.getPassword());

        // User 생성
        UserV2 user = UserV2.builder()
                .email(signupRequest.getEmail())
                .password(encryptPassword)
                .nickname(signupRequest.getNickname())
                .build();

        // user 정보에 Sns 정보 셋팅
        user.setSnsUser(snsUser);

        return userV2Repository.save(user).getId();
    }

    // 이메일 중복 확인
    private void existsEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw UserCustomException.ALREADY_EMAIL;
        }
    }

    // 닉네임 중복 확인
    private void existsNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw UserCustomException.ALREADY_NICKNAME;
        }
    }
}
