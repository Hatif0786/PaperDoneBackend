package com.exam.ExamPortalBackend.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.ExamPortalBackend.model.exam.Question;
import com.exam.ExamPortalBackend.model.exam.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	public Set<Question> findByQuiz(Long id);

}
