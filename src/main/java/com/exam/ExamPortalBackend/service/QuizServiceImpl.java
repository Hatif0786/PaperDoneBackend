package com.exam.ExamPortalBackend.service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamPortalBackend.exception.QuizAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.QuizDoesNotExistsException;
import com.exam.ExamPortalBackend.exception.UserDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Category;
import com.exam.ExamPortalBackend.model.exam.Quiz;
import com.exam.ExamPortalBackend.repository.CategoryRepository;
import com.exam.ExamPortalBackend.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	private QuizRepository repo;
	
	@Autowired
	private CategoryRepository crepo;
	
	
	
	@Override
	public Quiz addQuiz(Quiz q) throws QuizAlreadyExistsException {
		// TODO Auto-generated method stub
		Boolean r = this.repo.existsById(q.getqId());
		if(r) {
			throw new QuizAlreadyExistsException("Quiz already exists!!!");
		}
		else {
			return this.repo.save(q);
		}
	}

	@Override
	public void deleteQuiz(Long qId) throws QuizDoesNotExistsException{
		// TODO Auto-generated method stub
		Quiz q = new Quiz();
		q.setqId(qId);
		this.repo.delete(q);
		
	}

	@Override
	public Quiz updateQuiz(Quiz q, Long id) throws QuizDoesNotExistsException {
		// TODO Auto-generated method stub
		Optional<Quiz> r = this.repo.findById(id);
		if(r.get()!=null) {
			Quiz qu = r.get();
			qu.setTitle(q.getTitle());
			qu.setDescription(q.getDescription());
			qu.setNoOfQues(q.getNoOfQues());
			qu.setMaxMarks(q.getMaxMarks());
			qu.setActive(q.isActive());
			Optional<Category> c = this.crepo.findById(q.getCategory().getcid());
			if(c.get()!=null) {
				qu.setCategory(c.get());
			}
			return this.repo.save(qu);
		}
		else {
			throw new QuizDoesNotExistsException("Quiz does not exists!!!");
		}
		
	}

	@Override
	public Quiz getQuiz(Long qId) throws QuizDoesNotExistsException {
		// TODO Auto-generated method stub
		Optional<Quiz> r = this.repo.findById(qId);
		if(r.get()!=null) {
			return r.get();
		}
		else {
			throw new QuizDoesNotExistsException("Quiz does not exists!!!");
		}
	}

	@Override
	public Set<Quiz> getAllQuizzes() {
		// TODO Auto-generated method stub
		return new LinkedHashSet<>(this.repo.findAll());
	}

	@Override
	public Set<Quiz> getQuizzesByCategory(Long cid) {
		// TODO Auto-generated method stub
		Set<Quiz> allQuizzes = this.getAllQuizzes();
		
		Set<Quiz> result = new LinkedHashSet<>();
		for(Quiz q: allQuizzes) {
			if(q.getCategory().getcid()==cid) {
				result.add(q);
			}
		}
		return result;
	}

	@Override
	public Set<Quiz> getActiveQuizzes() {
		// TODO Auto-generated method stub
		return this.repo.findByActive(true);
	}

	@Override
	public Set<Quiz> getActiveAndCategory(Category c) {
		// TODO Auto-generated method stub
		
		Set<Quiz> result = this.repo.findByCategoryAndActive(c, true);
		return result;
	}

}
