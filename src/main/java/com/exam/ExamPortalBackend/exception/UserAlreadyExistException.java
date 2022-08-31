package com.exam.ExamPortalBackend.exception;

public class UserAlreadyExistException extends Exception {
	
	
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String message) {
        super(message);
    }
}
