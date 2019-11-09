package com.gp.learners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.AdminStaff;
import com.gp.learners.entities.Staff;
//import com.gp.learners.repositories.AdminStaffRepository;
import com.gp.learners.repositories.AdministrativeStaffRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.UserRepository;

@Service
public class AdminStaffService {
	
	@Autowired StaffRepository staffRepository;
	@Autowired AdministrativeStaffRepository adminStaffRepository;
	
	//Get Staff Details
	public AdminStaff getAdminStaffByUserId(Integer userId) {
		System.out.println("inAdminstaffService 1");
		Integer staffId=staffRepository.findByUserId(userId).getStaffId();
		System.out.println("inAdminstaffService 2");
		AdminStaff adminStaff=adminStaffRepository.getAdminStaffByStaffId(staffId);
		System.out.println("inAdminstaffService 3");
		return adminStaff;
	}
}
