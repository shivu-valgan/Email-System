package com.email.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.system.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
