package com.app.library.payload.response;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public java.lang.String getToken() {
        return token;
    }

    public void setToken(final java.lang.String token) {
        this.token = token;
    }

    public java.lang.String getType() {
        return type;
    }

    public void setType(final java.lang.String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public java.lang.String getUsername() {
        return username;
    }

    public void setUsername(final java.lang.String username) {
        this.username = username;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(final java.lang.String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }
}
