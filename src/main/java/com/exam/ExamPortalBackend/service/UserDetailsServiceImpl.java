package com.exam.ExamPortalBackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.ExamPortalBackend.model.User;
import com.exam.ExamPortalBackend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User u =this.userRepo.findByUsername(username);
		if(u==null) {
			throw new UsernameNotFoundException("Invalid Credentials!!!");
		}
		
		return u;
	}

}
