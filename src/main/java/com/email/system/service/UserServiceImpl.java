package com.email.system.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.email.system.dto.LoginRequestDto;
import com.email.system.dto.LoginResponseDto;
import com.email.system.dto.ResponseDto;
import com.email.system.dto.UserRequestDto;
import com.email.system.dto.UserResponseDto;
import com.email.system.entity.User;
import com.email.system.exception.DataExistsException;
import com.email.system.exception.DataNotFoundException;
import com.email.system.exception.InvalidCredentialsException;
import com.email.system.mapper.UserMapper;
import com.email.system.repository.UserRepository;
import com.email.system.security.CustomUserDetails;
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
	public UserResponseDto registerUser(UserRequestDto dto) {
		
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DataExistsException("Email already registered");
        }
        
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        User savedUser = userRepository.save(user);
        
        
        return userMapper.toUserResponseDto(savedUser);

        
	}
	

	@Override
	public LoginResponseDto loginUser(LoginRequestDto dto) {
		
		Authentication authentication;
		
		try {
		 authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						dto.getEmail(), 
						dto.getPassword()
						)
				);
		}catch(BadCredentialsException ex) {
			throw new InvalidCredentialsException("invalid Credentials");
		}
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		
		User user = userDetails.getUser();
		String token = jwtUtil.generateToken(user.getEmail(),user.getRole());
		
		return new LoginResponseDto(token);
	}
    

}
