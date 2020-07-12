package com.krunal.projectbrain.model;

import javax.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String lastname;

    @Column(nullable = true)
    private String location;

    @JsonIgnore
    @Column(unique = false, nullable = false)
    private String password;

    @ManyToMany
    private Set<User> followers;

    @OneToMany
    private Set<Idea> todo;

    @OneToMany
    @JsonIgnore
    private Set<Idea> ideas;
}