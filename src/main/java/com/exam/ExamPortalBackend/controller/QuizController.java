 package com.exam.ExamPortalBackend.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamPortalBackend.exception.QuizAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.QuizDoesNotExistsException;
import com.exam.ExamPortalBackend.exception.QuizDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Category;
import com.exam.ExamPortalBackend.model.exam.Quiz;
import com.exam.ExamPortalBackend.service.QuizService;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {
	
	@Autowired
	private QuizService service;
	
	@PostMapping
	private ResponseEntity<?> addQuiz(@RequestBody Quiz q){
		try {
			Quiz result = this.service.addQuiz(q);
			return new ResponseEntity<Quiz>(result, HttpStatus.CREATED);
		} catch (QuizAlreadyExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	
	@PutMapping("/{qId}")
	private ResponseEntity<?> updateQuiz(@RequestBody Quiz q, @PathVariable("qId") long id){
		try {
			Quiz result = this.service.updateQuiz(q, id);
			return new ResponseEntity<Quiz>(result, HttpStatus.OK);
		} catch (QuizDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping
	private ResponseEntity<?> getAllQuizzes(){
		Set<Quiz> result = this.service.getAllQuizzes();
		return new ResponseEntity<Set<Quiz>>(result, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{qId}")
	private ResponseEntity<?> getSingleQuiz(@PathVariable("qId") long id){
		try {
			Quiz q = this.service.getQuiz(id);
			return new ResponseEntity<Quiz>(q, HttpStatus.OK);
		} catch (QuizDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{qId}")
	private ResponseEntity<?> deleteQuiz(@PathVariable("qId") long id){
		try {
			this.service.deleteQuiz(id);
			return ResponseEntity.ok(null);
		} catch (QuizDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/active")
	private ResponseEntity<?> getActiveQuizzes(){
		Set<Quiz> result = this.service.getActiveQuizzes();
		return new ResponseEntity<Set<Quiz>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/category/active/{cid}")
	private ResponseEntity<?> getActiveandCategoryQuizzes(@PathVariable("cid") long id){
		Category c = new Category();
		c.setcid(id);
		Set<Quiz> result = this.service.getActiveAndCategory(c);
		return new ResponseEntity<Set<Quiz>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/category/{cid}")
	private ResponseEntity<?> getQuizzesByCategory(@PathVariable("cid") long id){
		Set<Quiz> result =  this.service.getQuizzesByCategory(id);
		return new ResponseEntity<Set<Quiz>>(result, HttpStatus.OK);
	}
}
