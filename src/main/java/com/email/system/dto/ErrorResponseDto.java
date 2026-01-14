package com.email.system.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorResponseDto {
	private String msg;
	private int status;
	private LocalDateTime timeStamp;
}
