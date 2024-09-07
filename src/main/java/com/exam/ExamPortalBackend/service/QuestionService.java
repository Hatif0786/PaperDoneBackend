package com.exam.ExamPortalBackend.service;

import java.util.Set;

import com.exam.ExamPortalBackend.exception.QuestionAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.QuestionDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Question;
import com.exam.ExamPortalBackend.model.exam.Quiz;

public interface QuestionService {
	
	public Question addQues(Question question) throws QuestionAlreadyExistsException;
	
	public void deleteQues(Long quesId) throws QuestionDoesNotExistsException;
	
	public Question getQues(Long quesId) throws QuestionDoesNotExistsException;
	
	public Set<Question> getAllQues();
	
	public Question updateQuestion(Question q, Long id) throws QuestionDoesNotExistsException;
	
	public Set<Question> getQuesByQuiz(Long qId) throws QuestionDoesNotExistsException;

}
