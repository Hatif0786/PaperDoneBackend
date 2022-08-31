package com.exam.ExamPortalBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.ExamPortalBackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	public User findByUsername(String username);
}
