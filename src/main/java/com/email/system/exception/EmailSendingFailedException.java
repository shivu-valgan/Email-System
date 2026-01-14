package com.email.system.exception;

public class EmailSendingFailedException extends RuntimeException {
	public EmailSendingFailedException(String msg) {
		super(msg);
	}

}
