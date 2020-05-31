package com.springlab.spring.boot.profile.service.controller;

import com.springlab.spring.boot.profile.service.model.User;
import com.springlab.spring.boot.profile.service.security.AuthTokenInfo;
import com.springlab.spring.boot.profile.service.security.SecurityTokenAccessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SecurityTokenAccessUtil securityTokenAccessUtil;

    @GetMapping("/getUsers")
    public List<Object> getUsers(){
        HttpHeaders headers = getHttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<Object> response = (ResponseEntity<Object>) restTemplate.exchange("http://localhost:9090/secured/api/users", HttpMethod.GET, httpEntity, Object.class);
        return (List)response.getBody();
    }

    @GetMapping("/getUser/{id}")
    public String getUserById(@PathVariable("id") Integer Id){
        HttpHeaders headers = getHttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<String> response = (ResponseEntity<String>) restTemplate.exchange("http://localhost:9090/secured/api/users/{id}", HttpMethod.GET, httpEntity, String.class, Id);
        return response.getBody();
    }

    @PostMapping("/createUser")
    public String addUser(@RequestBody User user){
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<User> httpEntity = new HttpEntity(user, headers);


        ResponseEntity<String> response = (ResponseEntity<String>) restTemplate.exchange("http://localhost:9090/secured/api/addUser", HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AuthTokenInfo authTokenInfo = securityTokenAccessUtil.getAccessToken();
        logger.info("AuthTokenInfo :"+authTokenInfo);

        if(null != authTokenInfo){
            headers.set("Authorization", "Bearer "+authTokenInfo.getAccess_token());
        }

        return headers;
    }
}
