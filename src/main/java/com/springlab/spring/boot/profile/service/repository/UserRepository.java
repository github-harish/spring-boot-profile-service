package com.springlab.spring.boot.profile.service.repository;

import com.springlab.spring.boot.profile.service.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private List<User> userList = new ArrayList<>();

    public User add(User user) {
        user.setUserId(userList.size()+1);
        userList.add(user);
        return user;
    }

    public User findById(Integer id){
        Optional<User> user = userList.stream().filter(a -> a.getUserId().equals(id)).findFirst();
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    public List<User> findAll() {
        return userList;
    }

}
