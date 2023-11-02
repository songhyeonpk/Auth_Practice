package com.shyeon.global.oauth.naver;

import com.shyeon.global.oauth.OAuthLoginParams;
import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class NaverLoginParams implements OAuthLoginParams {

    @Getter
    private String authorizationCode;

    @Getter
    private String state;

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeRequestBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", this.authorizationCode);
        body.add("state", this.state);

        return body;
    }
}
