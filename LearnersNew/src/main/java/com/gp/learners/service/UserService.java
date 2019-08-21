package com.gp.learners.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.User;
import com.gp.learners.repositories.UserRepository;



@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public String isValidLogin(String email,String password) {
		
		if( !email.equals("") && !password.equals("")) {
			User user=userRepository.findByEmailAndPassword(email, password);
			if(user.getStatus() == 1) {
				return "success";
			}
			else {
				return "deactivate";
			}
		}
		
		return "";
	}
	
}
