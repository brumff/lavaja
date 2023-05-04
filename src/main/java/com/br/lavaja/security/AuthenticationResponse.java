package com.br.lavaja.security;

public class AuthenticationResponse {
    private final String token;
    private final UserSS userInfo;

    public AuthenticationResponse(String token, UserSS userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public UserSS getUserInfo() {
        return userInfo;
    }
}