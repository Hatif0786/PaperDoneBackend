package com.exam.ExamPortalBackend.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.ExamPortalBackend.model.exam.Category;
import com.exam.ExamPortalBackend.model.exam.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	
	Set<Quiz> findByActive(Boolean b);
	
	Set<Quiz> findByCategoryAndActive(Category c, Boolean b);

}
