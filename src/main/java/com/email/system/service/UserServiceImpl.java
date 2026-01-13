package com.email.system.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.email.system.dto.LoginRequestDto;
import com.email.system.dto.ResponseDto;
import com.email.system.dto.UserRequestDto;
import com.email.system.entity.User;
import com.email.system.mapper.UserMapper;
import com.email.system.repository.UserRepository;
import com.email.system.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
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
	

	@Override
	public ResponseEntity<ResponseDto> loginUser(LoginRequestDto dto) {
		
		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(()->new RuntimeException("Invalid Credentials"));
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						dto.getEmail(), 
						dto.getPassword()
						)
				);
		String token = jwtUtil.generateToken(user.getEmail(),user.getRole());
		
		return ResponseEntity.status(200).body(new ResponseDto("Logged in Success", token));
	}
    

}
