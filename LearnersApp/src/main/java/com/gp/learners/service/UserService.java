package com.gp.learners.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.User;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StudentService studentService;
	
	public User isValidLogin(String email) {
		
		//deactivate student account which student's trial_date over
		new Thread(new Runnable() {
		    public void run() {
		    	studentService.deactivateStudentAccount();
		    }
		}).start();

		if( !email.equals("")) {
			if(isExistUser(email)) {
				return userRepository.findByEmail(email);
			}
		}
		return null;
	}
	
	private Boolean isExistUser(String email) {
		User user=userRepository.findByEmail(email);
		if(user != null) {
			return true;
		}
		return false;
	}
	
}
