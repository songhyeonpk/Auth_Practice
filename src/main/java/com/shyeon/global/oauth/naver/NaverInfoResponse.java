package com.shyeon.global.oauth.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shyeon.global.oauth.OAuthInfoResponse;
import com.shyeon.global.oauth.OAuthProvider;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {

        @JsonProperty("id")
        private String id;

        @JsonProperty("nickname")
        private String nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String getOAuthId() {
        return response.id;
    }

    @Override
    public String getOAuthNickname() {
        return response.nickname;
    }
}
