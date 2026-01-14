package com.email.system.controller;

import com.email.system.dto.UserRequestDto;
import com.email.system.dto.LoginRequestDto;
import com.email.system.dto.ResponseDto;
import com.email.system.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register( @Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(new ResponseDto("User Registered Successfully",userService.registerUser(dto)));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login( @Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK)
        		.body(new ResponseDto("Logged in Successfully",userService.loginUser(request)));
        		
    }
}

