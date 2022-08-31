package com.exam.ExamPortalBackend.exception;

public class UserDoesNotExistsException extends Exception {
	
	
	private static final long serialVersionUID = 1L;

	public UserDoesNotExistsException(String message) {
        super(message);
    }
}
