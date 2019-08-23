package com.gp.learners.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.User;
import com.gp.learners.repositories.UserRepository;



@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User isValidLogin(String email,String password) {
		User user=new User();
		if( !email.equals("") && !password.equals("")) {
			if(isExistUser(email, password)) {
				user =userRepository.findByEmailAndPassword(email, password);
				if(user.getStatus() == 1) {
					return user;
				}
				user.setUserId(0);//user account is deactivate
				return user;
			}
			user.setUserId(-1);//user record does not exist in the database
			return user;
		}
		user.setUserId(-2);//validation failed
		return user;
	}
	
	private Boolean isExistUser(String email,String password) {
		User user=userRepository.findByEmailAndPassword(email, password);
		if(user != null) {
			return true;
		}
		return false;
	}
	
}
