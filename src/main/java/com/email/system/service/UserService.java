package com.email.system.service;

import org.springframework.http.ResponseEntity;

import com.email.system.dto.UserRequestDto;
import com.email.system.dto.UserResponseDto;
import com.email.system.dto.LoginRequestDto;
import com.email.system.dto.LoginResponseDto;
import com.email.system.dto.ResponseDto;

public interface UserService {
	
	public UserResponseDto registerUser(UserRequestDto request);
	public LoginResponseDto loginUser(LoginRequestDto dto);

}
