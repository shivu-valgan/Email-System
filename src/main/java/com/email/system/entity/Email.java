package com.email.system.entity;

import java.time.LocalDateTime;

import com.email.system.enums.EmailStatus;
import com.email.system.enums.EmailType;
import com.email.system.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emails")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmail;
    private String toEmail;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status; // SENT, FAILED

    @Enumerated(EnumType.STRING)
    private EmailType type; // OUTBOUND, INBOUND

    private LocalDateTime sentAt;

    @ManyToOne
    private User user; // owner
}
