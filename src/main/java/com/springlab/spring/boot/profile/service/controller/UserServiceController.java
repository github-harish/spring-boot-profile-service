package com.springlab.spring.boot.profile.service.controller;

import com.springlab.spring.boot.profile.service.model.User;
import com.springlab.spring.boot.profile.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured/api")
public class UserServiceController {

    private static Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers(){
        logger.info("Get all available users :");
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        logger.info("Add new user : "+ user);
        return userRepository.add(user);
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable("id") Integer Id){
        logger.info("Find user by Id : "+Id);
        return userRepository.findById(Id);
    }
}
