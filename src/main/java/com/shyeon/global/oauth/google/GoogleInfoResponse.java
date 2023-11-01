package com.shyeon.global.oauth.google;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shyeon.global.oauth.OAuthInfoResponse;
import com.shyeon.global.oauth.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResponse implements OAuthInfoResponse {

    @JsonProperty("id")
    private String googleId;

    @JsonProperty("name")
    private String googleName;


    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public String getOAuthId() {
        return this.googleId;
    }

    @Override
    public String getOAuthNickname() {
        return this.googleName;
    }
}
