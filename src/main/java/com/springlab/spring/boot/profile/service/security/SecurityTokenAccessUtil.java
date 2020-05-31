package com.springlab.spring.boot.profile.service.security;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Component
public class SecurityTokenAccessUtil {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Autowired
    RestTemplate restTemplate;

    /**
     * Prepare Http Headers
     * @return
     */
    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        String plainClientCredentials = clientId+":"+clientSecret;
        String base64ClientCredentials = new String(Base64.encode(plainClientCredentials.getBytes()));

        headers.set("Authorization", "Basic "+base64ClientCredentials);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public AuthTokenInfo getAccessToken() {
        HttpEntity<String> httpEntity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<Object> response = restTemplate.exchange(accessTokenUri, HttpMethod.POST, httpEntity, Object.class);

        LinkedHashMap<String, Object> authMap = (LinkedHashMap<String, Object>)response.getBody();

        AuthTokenInfo tokenInfo = null;
        if(null != authMap){
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String) authMap.get("access_token"));
            tokenInfo.setToken_type((String) authMap.get("token_type"));
            tokenInfo.setRefresh_token((String) authMap.get("refresh_token"));
            tokenInfo.setExpires_in((Integer) authMap.get("expires_in"));
            tokenInfo.setScope((String) authMap.get("scope"));
        }

        return tokenInfo;
    }

}
