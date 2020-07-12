package com.krunal.projectbrain.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.krunal.projectbrain.form.UserForm;
import com.krunal.projectbrain.model.User;
import com.krunal.projectbrain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("/user/register")
    public User save(@RequestBody UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUsername(userForm.getUsername());
        user.setFirstname(userForm.getFirstname());
        user.setLastname(userForm.getLastname());
        user.setLocation(userForm.getLocation());

        User responseUser = userRepository.save(user);

        // Empty set instead of null to prevent,null pointer in android app
        Set<User> arrUsers = Collections.<User> emptySet();
        
        responseUser.setFollowers(arrUsers);
        return responseUser;
    }
}