package com.exam.ExamPortalBackend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamPortalBackend.config.JwtUtils;
import com.exam.ExamPortalBackend.model.JwtRequest;
import com.exam.ExamPortalBackend.model.JwtResponse;
import com.exam.ExamPortalBackend.model.User;


@CrossOrigin("*")
@RestController
public class AuthenticateController {
	
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	@PostMapping("/generate-token")
	private ResponseEntity<?> generateToken(@RequestBody JwtRequest request){
		
		try {
			this.authenticate(request.getUsername(), request.getPassword());
			UserDetails user =this.userDetailsService.loadUserByUsername(request.getUsername());
			String token =this.jwtUtils.generateToken(user);
			return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
			
		}
		
	}
	
	
	private void authenticate(String username, String password) throws Exception {
		
		
		try {
			authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e) {
			throw new Exception("User Disabled"+e.getMessage());
		}catch(BadCredentialsException e) {
			throw new Exception("Bad credentials"+e.getMessage());
		}
	}
	
	
	@GetMapping("/current-user")
	private ResponseEntity<?> getUser(Principal p){
		User u =(User) this.userDetailsService.loadUserByUsername(p.getName());
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

}
