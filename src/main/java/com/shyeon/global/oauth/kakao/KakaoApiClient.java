package com.shyeon.global.oauth.kakao;

import com.shyeon.global.oauth.OAuthApiClient;
import com.shyeon.global.oauth.OAuthInfoResponse;
import com.shyeon.global.oauth.OAuthLoginParams;
import com.shyeon.global.oauth.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.client_id}")
    private String clientId;

    @Value("${oauth.kakao.client_secret}")
    private String clientSecret;

    @Value("${oauth.kakao.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.kakao.request_uri.auth}")
    private String authUri;

    @Value("${oauth.kakao.request_uri.api}")
    private String apiUri;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        // 카카오 토큰 요청 기본 URL 설정
        WebClient webClient = WebClient.create(authUri);

        // 요청 값 셋팅
        MultiValueMap<String, String> requestParams = params.makeRequestBody();
        requestParams.add("grant_type", GRANT_TYPE);
        requestParams.add("client_id", clientId);
        requestParams.add("redirect_uri", redirectUri);
        requestParams.add("client_secret", clientSecret);

        // WebClient 객체를 이용해 카카오 OAuth 인증 서버로 Token 값 요청
        KakaoTokens kakaoTokens =
                webClient.post()
                        .uri("/oauth/token")
                        .headers(header -> {
                            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                            header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                            header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                        })
                        .bodyValue(requestParams)
                        .retrieve()
                        .bodyToMono(KakaoTokens.class)
                        .block();

        log.info("Access Token : " + kakaoTokens.getAccessToken());
        log.info("Refresh Token : " + kakaoTokens.getRefreshToken());
        log.info("Scope : " + kakaoTokens.getScope());

        assert kakaoTokens != null;
        return kakaoTokens.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOAuthUserInfo(String accessToken) {
        // 카카오 사용자 정보 요청 기본 URL 설정
        WebClient webClient = WebClient.create(apiUri);

        // 요청 값 셋팅
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("property_keys", "[\"kakao_account.profile\"]");

        // 카카오 OAuth 서버로 사용자 정보 요청, KakaoInfoResponse 객체로 바인딩
        KakaoInfoResponse response = webClient
                .post()
                .uri("/v2/user/me")
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                    header.set("Authorization", "Bearer " + accessToken);
                })
                .bodyValue(requestParams)
                .retrieve()
                .bodyToMono(KakaoInfoResponse.class)
                .block();

        log.info("KAKAO_ID : " + response.getOAuthId());
        log.info("KAKAO_NICKNAME : " + response.getOAuthNickname());

        return response;
    }
}
