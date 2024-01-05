package com.royalmail.exception;

import org.springframework.http.HttpStatus;

public class BarcodeValidationAPIException extends RuntimeException {

	private static final long serialVersionUID = 4893943955694501534L;
	private HttpStatus status;
	private String message;

	public BarcodeValidationAPIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
