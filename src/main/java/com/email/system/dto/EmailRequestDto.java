package com.email.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDto {

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String to;
	private String subject;
	private String body;
}
