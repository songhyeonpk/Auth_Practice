package com.shyeon.global.oauth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider getProvider();

    MultiValueMap<String, String> makeRequestBody();
}
