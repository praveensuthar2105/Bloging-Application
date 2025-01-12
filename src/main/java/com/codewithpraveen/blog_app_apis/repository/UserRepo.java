package com.codewithpraveen.blog_app_apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithpraveen.blog_app_apis.Entites.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);
}
