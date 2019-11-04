package com.gp.learners.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.AdminStaff;
import com.gp.learners.entities.Salary;
import com.gp.learners.entities.SalaryInformation;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.User;
import com.gp.learners.entities.WorkTime;
import com.gp.learners.entities.mapObject.StaffWorkDaysDataMap;
import com.gp.learners.repositories.AdministrativeStaffRepository;
import com.gp.learners.repositories.SalaryRepository;
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
	
	@PostMapping("/staff/attendance/{staffId}")
	public ResponseEntity<Integer> markStaffAttendance(@PathVariable("staffId") Integer staffId){
		Integer reply = staffService.markStaffAttendance(staffId);
		if(reply!=null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/staff/salary/information")
	public ResponseEntity<List<SalaryInformation>> getStaffSalaryInforamtion(){
		List<SalaryInformation> salaryInformationList = staffService.getStaffSalaryInforamtion();
		if(salaryInformationList!=null) {
			return ResponseEntity.ok(salaryInformationList);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/staff/salary/information/{salaryInformationId}")
	public ResponseEntity<SalaryInformation> getStaffSalaryInformation(@PathVariable("salaryInformationId") Integer salaryInformationId){
		SalaryInformation object = staffService.getStaffSalaryInformation(salaryInformationId);
		if(object!=null) {
			return ResponseEntity.ok(object);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("staff/salary/information")
	public ResponseEntity<Void> addStaffSalaryInformation(@RequestBody SalaryInformation object) {
		String reply = staffService.addStaffSalaryInformation(object);
		if(reply!=null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("staff/salary/information")
	public ResponseEntity<Void> updateStaffSalaryInformation(@Valid @RequestBody SalaryInformation object){
		String reply = staffService.updateStaffSalaryInformation(object);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("staff/salary/information/{salaryInformationId}")
	public ResponseEntity<Void> deleteStaffSalaryInformation(@PathVariable("salaryInformationId") Integer salaryInformationId){
		String reply = staffService.deleteStaffSalaryInformation(salaryInformationId);
		if(reply!=null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("staff/work/time")
	public ResponseEntity<WorkTime> getStaffWorkTime(){
		WorkTime workTime = staffService.getStaffWorkTime();
		if(workTime!=null) {
			return ResponseEntity.ok(workTime);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("staff/work/time/{fullDay}/{halfDay}")
	public ResponseEntity<Void> updateStaffWorkTime(@PathVariable("fullDay")Integer fullDay,@PathVariable("halfDay") Integer halfDay){
		String reply = staffService.updateStaffWorkTime(fullDay,halfDay);
		if(reply!=null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("staff/salary/{month}")
	public ResponseEntity<List<Salary>> getStaffSalaryDetails(@PathVariable("month") Integer month){
		List<Salary> salaryList = staffService.getStaffSalaryList(month);
		return ResponseEntity.ok(salaryList);
	}
	
	@GetMapping("staff/salary/data/{staffId}/{month}")
	public ResponseEntity<Salary> getStaffSalaryDetails(@PathVariable("staffId") Integer staffId,@PathVariable("month") Integer month) {
		Salary salary = staffService.getStaffSalaryData(staffId,month);
		if(salary!=null) {
			return ResponseEntity.ok(salary);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("staff/pay/salary")
	public ResponseEntity<Void> payStaffSalary(@Valid @RequestBody Salary object){
		String reply = staffService.payStaffSalary(object);
		if(reply!=null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("staff/work/days/{staffId}/{month}")
	public ResponseEntity<StaffWorkDaysDataMap> getStaffWorkDays(@PathVariable("staffId") Integer staffId,@PathVariable("month") Integer month){
		StaffWorkDaysDataMap object = staffService.getStaffWorkDays(staffId, month);
		if(object!=null) {
			return ResponseEntity.ok(object);
		}
		return ResponseEntity.notFound().build();
	}
	
}
