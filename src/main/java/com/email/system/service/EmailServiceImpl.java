package com.email.system.service;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.email.system.dto.EmailRequestDto;
import com.email.system.entity.Email;
import com.email.system.entity.User;
import com.email.system.enums.EmailStatus;
import com.email.system.enums.EmailType;
import com.email.system.exception.DataNotFoundException;
import com.email.system.repository.EmailRepository;
import com.email.system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

	@Override
	public void sendEmail(EmailRequestDto dto) {
		
		Authentication auth = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		String senderEmail = auth.getName();
		
		User sender = userRepository.findByEmail(senderEmail)
				.orElseThrow(()->new DataNotFoundException("User Not found"));
		
		Email email = Email.builder()
                .fromEmail(senderEmail)
                .toEmail(dto.getTo())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .type(EmailType.EXTERNAL)   // internal later
                .sentAt(LocalDateTime.now())
                .user(sender)
                .build();
		
		try {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(dto.getTo());
		message.setSubject(dto.getSubject());
		message.setText(dto.getBody());
		
		mailSender.send(message);
		email.setStatus(EmailStatus.SENT);
		} catch(Exception e) {
			email.setStatus(EmailStatus.FAILED);
		}
		
		emailRepository.save(email);
		
	}

}
