package com.bento.exception;

public class EmailAlreadyExistsException extends RuntimeException {

	public EmailAlreadyExistsException(final String message) {
		super(message);
	}

}
