package com.email.system.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.email.system.dto.ResponseDto;
import com.email.system.dto.UserRequestDto;
import com.email.system.entity.User;
import com.email.system.mapper.UserMapper;
import com.email.system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    
	@Override
	public ResponseEntity<ResponseDto> registerUser(UserRequestDto dto) {
		
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body(new ResponseDto("email already exists", null));
        }
        
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        User savedUser = userRepository.save(user);
        
        
        return ResponseEntity.status(201)
        		.body(new ResponseDto("User Registered Successfully",userMapper.toUserResponseDto(savedUser)));

        
	}
    

}
