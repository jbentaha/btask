package com.btask.exception;

public class EmailAlreadyExistsException extends RuntimeException {

	public EmailAlreadyExistsException(final String message) {
		super(message);
	}

}
