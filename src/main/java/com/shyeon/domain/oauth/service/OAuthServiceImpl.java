package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.*;
import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.Tokens;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.global.exception.customexception.UserCustomException;
import com.shyeon.global.oauth.*;
import com.shyeon.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final RequestOAuthApiClientService requestOAuthApiClientService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public OAuthAuthorizationDataResponseDto responseAuthData(String provider, OAuthRedirectDataDto data) {
        OAuthProvider oAuthProvider = OAuthProvider.convert(provider);

        if(oAuthProvider == OAuthProvider.NAVER) {
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

    // 닉네임 중복 확인
    private void existsNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw UserCustomException.ALREADY_NICKNAME;
        }
    }
}
