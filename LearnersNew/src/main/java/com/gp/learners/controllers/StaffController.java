package com.gp.learners.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.AdminStaff;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.AdministrativeStaffRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.service.StaffService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private StaffRepository staffRepostitory;
	
	@Autowired 
	private AdministrativeStaffRepository administrativeStaffRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/administrativestaff/register")
	public Object addAdministrativeStaff(@RequestParam Map<String,String> data) {
		
		//validation
		
		//Insert data to staff table
		Staff staff=new Staff();
		staff.setName(data.get("name"));
		staff.setNic(data.get("nic"));
		staff.setTel(data.get("tel"));
		staff.setAddress(data.get("address"));
			
		
		staff=staffRepostitory.save(staff);
		
		//Insert data to employee table
		AdminStaff administrativeStaff=new AdminStaff();
		//administrativeStaff.setStaffId(staff.getStaffId());
		administrativeStaff.setType(Integer.parseInt(data.get("role")));
		administrativeStaff.setQualification(data.get("qualification"));
		
		administrativeStaffRepository.save(administrativeStaff);
		
		
		//insert data to user
		User user=new User();
		user.setEmail(data.get("email"));
		user.setPassword(data.get("password"));
		user.setRegDate(new Date());
		user.setRole(Integer.parseInt(data.get("role")));
		user.setStatus(1);
		//user.setStaffId(staff.getStaffId());
		
		userRepository.save(user);
		
		return true;
	}
	
	@GetMapping("/staff")
	public Object getStaffDetails() {
		return staffService.getStaff();
	}
}
