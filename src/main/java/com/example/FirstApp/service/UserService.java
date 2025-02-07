package com.example.FirstApp.service;


import com.example.FirstApp.entity.User;
import com.example.FirstApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }
}
