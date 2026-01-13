package com.email.system.service;

import org.springframework.http.ResponseEntity;

import com.email.system.dto.UserRequestDto;
import com.email.system.dto.LoginRequestDto;
import com.email.system.dto.ResponseDto;

public interface UserService {
	
	public ResponseEntity<ResponseDto> registerUser(UserRequestDto request);
	public ResponseEntity<ResponseDto> loginUser(LoginRequestDto dto);

}
