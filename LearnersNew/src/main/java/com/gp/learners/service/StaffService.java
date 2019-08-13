package com.gp.learners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.repositories.UserRepository;

@Service
public class StaffService {
	
	@Autowired UserRepository userRepository;
	
	//Get Staff Details
	public Object getStaff() {
		return userRepository.findUserIdStaffIdNameRoleStatusByRoleNot(5);
	}
}
