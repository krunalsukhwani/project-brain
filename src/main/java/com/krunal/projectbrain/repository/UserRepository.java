package com.krunal.projectbrain.repository;

import com.krunal.projectbrain.model.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBrainByUsername(String username);
    Optional<User> findBrainByEmailAndPassword(String email, String password);
    //Optional<Brain> findBrainByEmail(String email);
}