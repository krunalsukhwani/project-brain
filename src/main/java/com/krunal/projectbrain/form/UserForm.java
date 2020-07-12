package com.krunal.projectbrain.form;

import lombok.Data;
import java.util.Set;

import com.krunal.projectbrain.model.User;

@Data
public class UserForm {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String location;
    private String password;
    private Set<User> data;
}