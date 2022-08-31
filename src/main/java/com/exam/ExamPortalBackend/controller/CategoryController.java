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

import com.exam.ExamPortalBackend.exception.CategoryAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.CategoryDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Category;
import com.exam.ExamPortalBackend.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	
	@PostMapping
	private ResponseEntity<?> addCategory(@RequestBody Category c){
		try {
			Category result = this.service.addCategory(c);
			return new ResponseEntity<Category>(result, HttpStatus.CREATED);
		} catch (CategoryAlreadyExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	
	@GetMapping
	private ResponseEntity<?> getAllCategories(){
		Set<Category> result = this.service.getAllCategories();
		return new ResponseEntity<Set<Category>>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{cId}")
	private ResponseEntity<?> deleteCategory(@PathVariable("cId") Long id){
		try {
			this.service.deleteCategory(id);
			return ResponseEntity.ok(null);
		} catch (CategoryDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/{cId}")
	private ResponseEntity<?> getCategory(@PathVariable("cId") long id){
		try {
			Category result = this.service.getCategory(id);
			return new ResponseEntity<Category>(result, HttpStatus.OK);
		} catch (CategoryDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	
	@PutMapping("/{cId}")
	private ResponseEntity<?> updateCatetgory(@RequestBody Category c, @PathVariable("cId") long id){
		try {
			Category result = this.service.updateCategory(c, id);
			return new ResponseEntity<Category>(result, HttpStatus.OK);
		} catch (CategoryDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

}
