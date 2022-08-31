package com.exam.ExamPortalBackend.controller;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
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

import com.exam.ExamPortalBackend.exception.QuestionAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.QuestionDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Question;
import com.exam.ExamPortalBackend.service.QuestionService;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {
	
	@Autowired
	private QuestionService service;
	
	@PostMapping
	private ResponseEntity<?> addQuestion(@RequestBody Question q){
		try {
			Question result= this.service.addQues(q);
			return new ResponseEntity<Question>(result, HttpStatus.CREATED);
		} catch (QuestionAlreadyExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	
	@GetMapping
	private ResponseEntity<?> getQuestions(){
		Set<Question> result = this.service.getAllQues();
		return new ResponseEntity<Set<Question>>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{quesId}")
	private ResponseEntity<?> deleteQuestion(@PathVariable("quesId") long id){
		try {
			this.service.deleteQues(id);
			return ResponseEntity.ok(null);
		} catch (QuestionDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/{quesId}")
	private ResponseEntity<?> getQuestion(@PathVariable("quesId") long id){
		try {
			Question q = this.service.getQues(id);
			return new ResponseEntity<Question>(q, HttpStatus.OK);
		} catch (QuestionDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/quiz/{qId}")
	private ResponseEntity<?> getQuestionByQuiz(@PathVariable("qId") long id){
		Set<Question> result;
		try {
			result = this.service.getQuesByQuiz(id);
			result.forEach((q)-> {
				q.setAnswer("");
			});
			return new ResponseEntity<Set<Question>>(result, HttpStatus.OK);
		} catch (QuestionDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@PutMapping("/{quesId}")
	private ResponseEntity<?> updateQuestion(@RequestBody Question q, @PathVariable("quesId") long id){
		try {
			Question result = this.service.updateQuestion(q, id);
			return new ResponseEntity<Question>(result, HttpStatus.OK);
		} catch (QuestionDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		
		}
	}
	
	
	@PostMapping("/eval-quiz")
	private ResponseEntity<?> evaluateQuiz(@RequestBody Set<Question> qls){
		double marksGot;
		double correctAnswers=0;
		double attempted=0;
		Set<Double> result = new LinkedHashSet<>();
		double singleQuesMarks = Double.parseDouble(qls.stream().findFirst().get().getQuiz().getMaxMarks())/Double.parseDouble(qls.stream().findFirst().get().getQuiz().getNoOfQues());
		
		for(Question q: qls) {
			try {
				Question question = this.service.getQues(q.getQuesId());
				if(question.getAnswer().trim().equals(q.getSelectedAnswer().trim())) {
					correctAnswers++;	
				}
				if(!q.getSelectedAnswer().equals("") || q.getSelectedAnswer() != null) {
					attempted++;
				}
				
			} catch (QuestionDoesNotExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		marksGot = singleQuesMarks* correctAnswers;
		result.add(marksGot);
		result.add(correctAnswers);
		result.add(attempted);
		
		return new ResponseEntity<Set<Double>>(result, HttpStatus.OK);
	}
	
}
