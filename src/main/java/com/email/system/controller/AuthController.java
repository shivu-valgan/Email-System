package com.email.system.controller;

import com.email.system.dto.UserRequestDto;
import com.email.system.dto.ResponseDto;
import com.email.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserRequestDto dto) {
        return userService.registerUser(dto);
    }
}

