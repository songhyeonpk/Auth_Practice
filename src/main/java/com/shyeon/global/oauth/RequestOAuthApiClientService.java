package com.shyeon.global.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// OAuth 서버로 API 요청 로직을 처리할 서비스
@Component
public class RequestOAuthApiClientService {

    private final Map<OAuthProvider, OAuthApiClient> clients;

    // 생성자 호출 시 불변의 Map 을 생성
    // OAuthApiClient 의 oAuthProvider() 메소드로 OAuthProvider Key 값을 설정하고 해당 메소드를 실행한 객체를 Value 로 저장하여 한쌍으로 관리
    public RequestOAuthApiClientService(List<OAuthApiClient> client) {
        this.clients = client.stream()
                .collect(Collectors.toUnmodifiableMap(oAuthApiClient -> oAuthApiClient.oAuthProvider(), Function.identity()));
    }

    // OAuth 서버로 로그인 요청을 보내는 로직
    // 각각 OAuth 의 요청 값으로 토큰 값을 요청하고, 해당 토큰을 통해 요청한 사용자의 정보를 가져오는 로직
    public OAuthInfoResponse requestOAuth(OAuthLoginParams oAuthLoginParams) {
        OAuthApiClient oAuthApiClient = this.clients.get(oAuthLoginParams.getProvider());

        String accessToken = oAuthApiClient.requestAccessToken(oAuthLoginParams);

        return oAuthApiClient.requestOAuthUserInfo(accessToken);
    }
}
