package com.exam.ExamPortalBackend.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamPortalBackend.exception.UserAlreadyExistException;
import com.exam.ExamPortalBackend.exception.UserDoesNotExistsException;
import com.exam.ExamPortalBackend.model.Role;
import com.exam.ExamPortalBackend.model.User;
import com.exam.ExamPortalBackend.model.UserRole;
import com.exam.ExamPortalBackend.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@CrossOrigin(origins="*")
	@PostMapping
	private ResponseEntity<?> createUser(@RequestBody User user){
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role r = new Role();
		r.setRoleId(1L);
		r.setRoleName("NORMAL");
		
		UserRole ur = new UserRole();
		ur.setUser(user);
		ur.setRole(r);
		
		Set<UserRole> userroles = new HashSet<>();
		userroles.add(ur);
		try {
			User result = this.service.createUser(user, userroles);
			return new ResponseEntity<User>(result, HttpStatus.CREATED);
		} catch (UserAlreadyExistException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
		
	}
	
	@GetMapping("/{username}")
	private ResponseEntity<?> getUser(@PathVariable("username") String username){
		try {
			User result = this.service.getUser(username);
			return new ResponseEntity<User>(result, HttpStatus.OK);
		} catch (UserDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update/{id}")
	private ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user){
		try {
			User result = this.service.updateUser(id, user);
			return new ResponseEntity<User>(result, HttpStatus.OK);
		} catch (UserDoesNotExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}

}
