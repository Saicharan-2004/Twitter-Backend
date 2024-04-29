package com.example.TwitterProject.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByEmail(String email);
}