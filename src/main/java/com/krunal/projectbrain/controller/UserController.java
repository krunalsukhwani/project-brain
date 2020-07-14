package com.krunal.projectbrain.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.krunal.projectbrain.form.FollowForm;
import com.krunal.projectbrain.form.IdeaForm;
import com.krunal.projectbrain.form.IdeaResponseForm;
import com.krunal.projectbrain.form.UserForm;
import com.krunal.projectbrain.model.Idea;
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

    @GetMapping(value = "/user/{username}/ideas")
    public IdeaResponseForm getIdeasForUser(@PathVariable String username) {
        IdeaResponseForm responseForm = new IdeaResponseForm();
        try {
            responseForm.setData(userRepository.findUserByUsername(username).orElseThrow().getIdeas());
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setData(new HashSet<Idea>());
        }
        return responseForm;
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
        Set<User> arrUsers = Collections.<User> emptySet();
        responseUser.setFollowers(arrUsers);
        return responseUser;
    }

    @PostMapping("/user/login")
    public User login(@RequestBody UserForm userForm) {
        User user = userRepository.findUserByEmailAndPassword(userForm.getEmail(), userForm.getPassword()).orElseThrow();
        return user;
    }

    @PostMapping("/user/update")
    public User updateUser(@RequestBody UserForm userForm) {
        User user = userRepository.findUserByUsername(userForm.getUsername()).orElseThrow();
        user.setFirstname(userForm.getFirstname());
        user.setLastname(userForm.getLastname());
        user.setLocation(userForm.getLocation());
        User responseUser = userRepository.save(user);
        return responseUser;
    }

    @PostMapping("/user/follow")
    public User follow(@RequestBody FollowForm followForm) {
        User firstUser = userRepository.findUserByUsername(followForm.getUsername()).orElseThrow();
        User secondUser = userRepository.findUserByUsername(followForm.getOtherUsername()).orElseThrow();
        firstUser.getFollowers().add(secondUser);
        User responseUser = userRepository.save(firstUser);
        return responseUser;
    }

    @GetMapping("/user/{username}/followers")
    public UserForm getFollowersForUser(@PathVariable String username) {
        UserForm responseForm = new UserForm();
        try {
            responseForm.setData(userRepository.findUserByUsername(username).orElseThrow().getFollowers());
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setData(new HashSet<User>());
        }
        return responseForm;
    }

    @GetMapping("/user/{username}/todos")
    public IdeaResponseForm getTodosForBrain(@PathVariable String username) {
        IdeaResponseForm responseForm = new IdeaResponseForm();
        try {
            responseForm.setData(userRepository.findUserByUsername(username).orElseThrow().getTodo());
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setData(new HashSet<Idea>());
        }
        return responseForm;
    }
}