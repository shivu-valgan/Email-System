package com.email.system.service;

import com.email.system.dto.EmailRequestDto;

public interface EmailService {
	public void sendEmail(EmailRequestDto dto);
}
