package com.gp.learners.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.AdminStaff;
import com.gp.learners.entities.Pdf;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.AdministrativeStaffRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.service.AdminStaffService;
import com.gp.learners.service.StaffService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class AdminStaffController {

	@Autowired
	private AdminStaffService adminStaffService;

	@GetMapping("adminByUserId/{userId}")
	public ResponseEntity<AdminStaff> getAdminstaffbyUserId(@PathVariable("userId") Integer userId) {
		System.out.println("inAdminstaffController");
		AdminStaff adminStaff = adminStaffService.getAdminStaffByUserId(userId);
		if (adminStaff != null) {
			return ResponseEntity.ok(adminStaff);
		}
		return ResponseEntity.noContent().build();
	}

}
