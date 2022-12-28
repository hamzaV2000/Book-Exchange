package com.example.demo.exception_handling;

import java.time.LocalDate;

public class MyErrorResponse {
	private int status;
	private String message;
	private LocalDate timstamp;
	public MyErrorResponse() {
		super();
	}

	public MyErrorResponse(int status, String message, LocalDate timstamp) {
		this.status = status;
		this.message = message;
		this.timstamp = timstamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getTimstamp() {
		return timstamp;
	}

	public void setTimstamp(LocalDate timstamp) {
		this.timstamp = timstamp;
	}
}
