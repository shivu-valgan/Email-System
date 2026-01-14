package com.email.system.dto;

import java.time.LocalDateTime;

import com.email.system.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({ "id", "fullName", "email", "role", "createdAt" })
public class UserResponseDto {
	private Long id;
	private String fullName;
	private String email;
	private UserRole role;
	private boolean enabled;
	private LocalDateTime createdAt;

}
