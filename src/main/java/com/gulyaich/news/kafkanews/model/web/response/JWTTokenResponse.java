package com.gulyaich.news.kafkanews.model.web.response;

public class JWTTokenResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JWTTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
