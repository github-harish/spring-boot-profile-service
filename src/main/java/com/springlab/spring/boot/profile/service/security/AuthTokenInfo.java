package com.springlab.spring.boot.profile.service.security;

import lombok.Data;

@Data
public class AuthTokenInfo {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String scope;
}
