package com.exam.ExamPortalBackend.service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamPortalBackend.exception.QuestionAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.QuestionDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Question;
import com.exam.ExamPortalBackend.model.exam.Quiz;
import com.exam.ExamPortalBackend.repository.QuestionRepository;
import com.exam.ExamPortalBackend.repository.QuizRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository repo;
	
	@Autowired
	private QuizRepository qrepo;

	@Override
	public Question addQues(Question question) throws QuestionAlreadyExistsException {
		// TODO Auto-generated method stub
		Boolean r = this.repo.existsById(question.getQuesId());
		if(r) {
			throw new QuestionAlreadyExistsException("Question already exists!!!");
		}
		
		else {
			return this.repo.save(question);
		}
	}

	@Override
	public void deleteQues(Long quesId) throws QuestionDoesNotExistsException {
		// TODO Auto-generated method stub
		Boolean r = this.repo.existsById(quesId);
		if(!r){
			throw new QuestionDoesNotExistsException("Question does not exists!!!");
		}
		
		else {
			this.repo.deleteById(quesId);
		}
		
		
	}

	@Override
	public Question getQues(Long quesId) throws QuestionDoesNotExistsException {
		// TODO Auto-generated method stub
		
		Optional<Question> r = this.repo.findById(quesId);
		if(r.get()==null) {
			throw new QuestionDoesNotExistsException("Question already exists!!!");
		}
		else {
			return r.get();
		}
	}

	@Override
	public Set<Question> getAllQues() {
		// TODO Auto-generated method stub
		return new LinkedHashSet<>(this.repo.findAll());
	}

	@Override
	public Set<Question> getQuesByQuiz(Long qId) throws QuestionDoesNotExistsException {
		// TODO Auto-generated method stub

		Optional<Quiz> q = this.qrepo.findById(qId);
		if(q.get()!=null) {
			Set<Question> r = q.get().getQuestions();
			return r;
		}
		else {
			throw new QuestionDoesNotExistsException("No questions for this quiz !!!");
		}
	}

	@Override
	public Question updateQuestion(Question q, Long id) throws QuestionDoesNotExistsException {
		// TODO Auto-generated method stub
		
		Optional<Question> r = this.repo.findById(id);
		if(r.get()!=null) {
			Question ques = r.get();
			ques.setContent(q.getContent());
			ques.setImage(q.getImage());
			ques.setOption1(q.getOption1());
			ques.setOption2(q.getOption2());
			ques.setOption3(q.getOption3());
			ques.setOption4(q.getOption4());
			ques.setAnswer(q.getAnswer());
			Optional<Quiz> qu = this.qrepo.findById(q.getQuiz().getqId());
			if(qu.get()!=null) {
				ques.setQuiz(qu.get());
			}
			return this.repo.save(ques);
		}
		else {
			throw new QuestionDoesNotExistsException("Question does not exists!!!!");
		}
	}
	
	

}
