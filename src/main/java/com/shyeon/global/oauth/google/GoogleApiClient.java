package com.shyeon.global.oauth.google;

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

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
@Slf4j
public class GoogleApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.google.client_id}")
    private String clientId;

    @Value("${oauth.google.client_secret}")
    private String clientSecret;

    @Value("${oauth.google.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.google.request_uri.auth}")
    private String authUri;

    @Value("${oauth.google.request_uri.api}")
    private String apiUri;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        WebClient webClient = WebClient.create(authUri);

        MultiValueMap<String, String> requestParams = params.makeRequestBody();
        requestParams.add("grant_type", GRANT_TYPE);
        requestParams.add("client_id", clientId);
        requestParams.add("client_secret", clientSecret);
        requestParams.add("redirect_uri", redirectUri);

        GoogleTokens tokens = webClient.post()
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(requestParams)
                .retrieve()
                .bodyToMono(GoogleTokens.class)
                .block();

        log.info("Google Access_Token : " + tokens.getAccessToken());
        log.info("Google Refresh_Token : " + tokens.getRefreshToken());
        log.info("Google Access_Token Expires_In : " + tokens.getExpiresIn());
        log.info("Google Scope : " + tokens.getScope());
        log.info("Google Token_Type : " + tokens.getTokenType());

        assert tokens != null;
        return tokens.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOAuthUserInfo(String accessToken) {
        log.info(accessToken);
        WebClient webClient = WebClient.create(apiUri);

        GoogleInfoResponse response = webClient.get()
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(
                            Collections.singletonList(StandardCharsets.UTF_8));
                    header.set("Authorization", "Bearer " + accessToken);
                })
                .retrieve()
                .bodyToMono(GoogleInfoResponse.class)
                .block();

        log.info(response.getOAuthId());
        log.info(response.getOAuthNickname());

        return response;
    }
}
