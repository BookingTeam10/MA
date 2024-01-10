package com.example.shopapp.model.login;

public class Token {
    private String jwt;
    private static Token instance;

    public Token(){}

    public static Token getInstance() {
        if (instance == null) {
            instance = new Token();
        }
        return instance;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
