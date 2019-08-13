package com.gp.learners.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.User;
import com.gp.learners.repositories.UserRepository;



@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public Object isValidLogin(String email,String password) {
		
		if(email.equals("") && password.equals("")) {
			return "400";
		}
		
		return userRepository.findByEmailAndPassword(email, password);
	}
	
}
