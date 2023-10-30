package com.shyeon.domain.oauth.service;

import com.shyeon.domain.oauth.dto.*;
import com.shyeon.domain.user.domain.User;
import com.shyeon.domain.user.dto.Tokens;
import com.shyeon.domain.user.repository.UserRepository;
import com.shyeon.global.oauth.OAuthInfoResponse;
import com.shyeon.global.oauth.OAuthLoginParams;
import com.shyeon.global.oauth.RequestOAuthApiClientService;
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
    public OAuthLoginResponseDto oAuthLogin(OAuthLoginParams oAuthLoginParams) {
        OAuthInfoResponse response = requestOAuthApiClientService.requestOAuth(oAuthLoginParams);

        return responseOAuthLogin(response);
    }

    private OAuthLoginResponseDto responseOAuthLogin(OAuthInfoResponse oAuthInfoResponse) {
        User user = userRepository.findByEmail(oAuthInfoResponse.getOAuthProvider() + "_" + oAuthInfoResponse.getOAuthId())
                .orElse(null);

        if(user != null) {
            String accessToken = jwtProvider.generateAccessToken(user.getEmail());

            return OAuthLoginSuccessResponseDto.of(user, Tokens.from(accessToken));
        }

        return OAuthLoginFailResponseDto.of(oAuthInfoResponse, "회원가입이 필요합니다.");
    }
}
