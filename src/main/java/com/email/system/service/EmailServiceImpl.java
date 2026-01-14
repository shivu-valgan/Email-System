package com.email.system.service;

import java.time.LocalDateTime;
import java.util.Optional;

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
import com.email.system.exception.EmailSendingFailedException;
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
		
		Authentication auth = 
				SecurityContextHolder.getContext().getAuthentication();
		
		String senderEmail = auth.getName();
		
		User sender = userRepository.findByEmail(senderEmail)
				.orElseThrow(()->new DataNotFoundException("User Not found"));
		
		Optional<User> receiverOpt = userRepository.findByEmail(dto.getTo());
		
		if(receiverOpt.isPresent()) {
			User receiver = receiverOpt.get();

            // Sender copy (OUTBOUND)
            Email senderCopy = Email.builder()
                    .fromEmail(senderEmail)
                    .toEmail(dto.getTo())
                    .subject(dto.getSubject())
                    .body(dto.getBody())
                    .type(EmailType.EXTERNAL)
                    .status(EmailStatus.SENT)
                    .sentAt(LocalDateTime.now())
                    .user(sender)
                    .build();

            // Receiver copy (INBOUND)
            Email receiverCopy = Email.builder()
                    .fromEmail(senderEmail)
                    .toEmail(dto.getTo())
                    .subject(dto.getSubject())
                    .body(dto.getBody())
                    .type(EmailType.INTERNAL)
                    .status(EmailStatus.RECEIVED)
                    .sentAt(LocalDateTime.now())
                    .user(receiver)
                    .build();

            emailRepository.save(senderCopy);
            emailRepository.save(receiverCopy);

            return;
		}
		
		Email email = Email.builder()
                .fromEmail(senderEmail)
                .toEmail(dto.getTo())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .type(EmailType.EXTERNAL)   
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
			throw new EmailSendingFailedException("Failed to send email");
		}
		emailRepository.save(email);
		
	}

}
