package com.exam.ExamPortalBackend.service;

import java.util.Set;

import com.exam.ExamPortalBackend.exception.UserAlreadyExistException;
import com.exam.ExamPortalBackend.exception.UserDoesNotExistsException;
import com.exam.ExamPortalBackend.model.User;
import com.exam.ExamPortalBackend.model.UserRole;

public interface UserService {
	
	public User createUser(User user, Set<UserRole> userrole) throws UserAlreadyExistException;
	
	public User getUser(String username) throws UserDoesNotExistsException;
	
	public User updateUser(Long id, User user) throws UserDoesNotExistsException;

}
