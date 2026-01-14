package com.email.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.system.dto.EmailRequestDto;
import com.email.system.service.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails")
public class EmailConroller {
	private final EmailService emailService;
	
	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailRequestDto dto){
		emailService.sendEmail(dto);
		return ResponseEntity.ok("Email sent Successfully");
	}
}
