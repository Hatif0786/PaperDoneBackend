package com.exam.ExamPortalBackend.service;

import java.util.Set;

import com.exam.ExamPortalBackend.exception.QuizAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.QuizDoesNotExistsException;
import com.exam.ExamPortalBackend.exception.UserDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Category;
import com.exam.ExamPortalBackend.model.exam.Quiz;

public interface QuizService {
	
	public Quiz addQuiz(Quiz q) throws QuizAlreadyExistsException;
	
	public void deleteQuiz(Long qId) throws QuizDoesNotExistsException;
	
	public Quiz updateQuiz(Quiz q, Long id) throws QuizDoesNotExistsException;
	
	public Quiz getQuiz(Long qId) throws QuizDoesNotExistsException;
	
	public Set<Quiz> getQuizzesByCategory(Long cid);
	
	public Set<Quiz> getActiveQuizzes();
	
	public Set<Quiz> getActiveAndCategory(Category c);
	
	public Set<Quiz> getAllQuizzes();


}
