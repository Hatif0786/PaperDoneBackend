package com.exam.ExamPortalBackend.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamPortalBackend.exception.UserAlreadyExistException;
import com.exam.ExamPortalBackend.exception.UserDoesNotExistsException;
import com.exam.ExamPortalBackend.model.User;
import com.exam.ExamPortalBackend.model.UserRole;
import com.exam.ExamPortalBackend.repository.RoleRepository;
import com.exam.ExamPortalBackend.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	
	@Autowired
	RoleRepository roleRepo;

	@Override
	public User createUser(User user, Set<UserRole> userrole) throws UserAlreadyExistException{
		// TODO Auto-generated method stub
		User response = this.userRepo.findByUsername(user.getUsername());
		if(response!=null) {
			throw new UserAlreadyExistException("User Already Exists!!!!");
		}
		for(UserRole ur: userrole) {
			roleRepo.save(ur.getRole());
		}
		
		user.getUserRoles().addAll(userrole);
		User result = this.userRepo.save(user);
		return result;
	}

	@Override
	public User getUser(String username) throws UserDoesNotExistsException {
		// TODO Auto-generated method stub
		
		User response = this.userRepo.findByUsername(username);
		if(response==null) {
			throw new UserDoesNotExistsException("User Not Found");
		}
		else {
			return response;
		}
	}

	@Override
	public User updateUser(Long id, User user) throws UserDoesNotExistsException {
		// TODO Auto-generated method stub
		
		Optional<User> response = this.userRepo.findById(id);
		if(response==null) {
			throw new UserDoesNotExistsException("User Not Found!!!");
		}
		User fetchUser=response.get();
		fetchUser.setUsername(user.getUsername());
		fetchUser.setPassword(user.getPassword());
		fetchUser.setFirstName(user.getFirstName());
		fetchUser.setLastName(user.getLastName());
		fetchUser.setProfile(user.getProfile());
		fetchUser.setEmail(user.getEmail());
		fetchUser.setPhone(user.getPhone());
		this.userRepo.save(fetchUser);
		return fetchUser;
	}

}
