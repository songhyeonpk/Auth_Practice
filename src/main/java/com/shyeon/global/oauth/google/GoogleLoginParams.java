package com.shyeon.global.oauth.google;

import com.shyeon.global.oauth.OAuthLoginParams;
import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GoogleLoginParams implements OAuthLoginParams {

    @Getter private String authorizationCode;

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public MultiValueMap<String, String> makeRequestBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", this.authorizationCode);

        return body;
    }
}
