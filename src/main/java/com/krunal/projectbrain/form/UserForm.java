package com.krunal.projectbrain.form;

import lombok.Data;

@Data
public class UserForm {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String location;
    private String password;
}