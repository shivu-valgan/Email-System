package com.email.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
