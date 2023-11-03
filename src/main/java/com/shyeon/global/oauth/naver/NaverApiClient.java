package com.shyeon.global.oauth.naver;

import com.shyeon.global.oauth.OAuthApiClient;
import com.shyeon.global.oauth.OAuthInfoResponse;
import com.shyeon.global.oauth.OAuthLoginParams;
import com.shyeon.global.oauth.OAuthProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
@Slf4j
public class NaverApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.naver.client_id}")
    private String clientId;

    @Value("${oauth.naver.client_secret}")
    private String clientSecret;

    @Value("${oauth.naver.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.naver.request_uri.auth}")
    private String authUri;

    @Value("${oauth.naver.request_uri.api}")
    private String apiUri;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        WebClient webClient = WebClient.create(authUri);

        MultiValueMap<String, String> requestParams = params.makeRequestBody();
        requestParams.add("grant_type", GRANT_TYPE);
        requestParams.add("client_id", clientId);
        requestParams.add("client_secret", clientSecret);

        NaverTokens tokens = webClient.post()
                .headers(header -> {
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(requestParams)
                .retrieve()
                .bodyToMono(NaverTokens.class)
                .block();

        log.info(tokens.getAccessToken());
        log.info(tokens.getRefreshToken());

        assert tokens != null;
        return tokens.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOAuthUserInfo(String accessToken) {
        WebClient webClient = WebClient.create(apiUri);

        NaverInfoResponse response = webClient.get()
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(NaverInfoResponse.class)
                .block();

        log.info(response.getOAuthId());
        log.info(response.getOAuthNickname());

        return response;
    }
}
