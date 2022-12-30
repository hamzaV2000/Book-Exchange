package com.example.demo.exception_handling;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	  public ResponseEntity<?> handleError(HttpServletRequest req, Exception ex) {
		MyErrorResponse response = new MyErrorResponse(HttpStatus.BAD_REQUEST.value(), "error", LocalDate.now());
		return ResponseEntity.badRequest().body(response);
	  }
	@ExceptionHandler(MyException.class)
	public ResponseEntity<?> handleMyError(HttpServletRequest req, Exception ex) {
		MyErrorResponse response = new MyErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDate.now());
		return ResponseEntity.badRequest().body(response);
	}
	@ExceptionHandler(NullPointerException.class)
	  public ResponseEntity<?> handleNullError(HttpServletRequest req, Exception ex) {
		MyErrorResponse response = new MyErrorResponse(HttpStatus.BAD_REQUEST.value(), "error", LocalDate.now());
		return ResponseEntity.badRequest().body(response);
	  }
}
