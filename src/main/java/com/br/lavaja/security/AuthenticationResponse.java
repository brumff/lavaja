package com.br.lavaja.security;

public class AuthenticationResponse {
    private final String token;
    private final UserSS userInfo;
    private final String nomeUsuario;

    public AuthenticationResponse(String token, UserSS userInfo) {
        this.token = token;
        this.userInfo = userInfo;
        this.nomeUsuario = userInfo.getNome();
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getToken() {
        return token;
    }

    public UserSS getUserInfo() {
        return userInfo;
    }
}