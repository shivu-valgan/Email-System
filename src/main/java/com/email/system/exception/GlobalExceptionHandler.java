package com.email.system.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.email.system.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
//
//        ErrorResponseDto error = new ErrorResponseDto(
//                "Internal Server Error",
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                LocalDateTime.now()
//        );
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
	
	@ExceptionHandler(DataExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleDataExistsException(DataExistsException ex) {

        ErrorResponseDto error = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
	
	@ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDataExistsException(DataNotFoundException ex) {

        ErrorResponseDto error = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
	
	@ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(InvalidCredentialsException ex) {

        ErrorResponseDto error = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationErrors(
	        MethodArgumentNotValidException ex) {

	    String message = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(err -> err.getField() + ": " + err.getDefaultMessage())
	            .findFirst()
	            .orElse("Validation error");

	    ErrorResponseDto error = new ErrorResponseDto(
	            message,
	            HttpStatus.BAD_REQUEST.value(),
	            LocalDateTime.now()
	    );

	    return ResponseEntity.badRequest().body(error);
	}

}
