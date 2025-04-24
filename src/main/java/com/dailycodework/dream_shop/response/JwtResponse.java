package com.dailycodework.dream_shop.response;


public class JwtResponse {
    private Long id;
    private String token;

    public JwtResponse(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public JwtResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
