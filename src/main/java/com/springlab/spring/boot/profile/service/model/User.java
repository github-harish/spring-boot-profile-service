package com.springlab.spring.boot.profile.service.model;

import lombok.Data;

@Data
public class User {

    private Integer userId;
    private String userName;
    private Integer age;
    private String address;

    public User(){

    }

    public User(String userName, Integer age, String address){
        this.userName = userName;
        this.age = age;
        this.address = address;
    }


}
