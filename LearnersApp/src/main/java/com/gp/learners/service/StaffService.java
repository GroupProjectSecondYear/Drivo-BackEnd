package com.gp.learners.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Admin;
import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.SalaryInformation;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.WorkTime;
import com.gp.learners.repositories.AdminRepository;
import com.gp.learners.repositories.AttendanceRepository;
import com.gp.learners.repositories.SalaryInformationRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.repositories.WorkTimeRepository;

import ch.qos.logback.core.util.Duration;

@Service
public class StaffService {
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	@Autowired 
	AttendanceRepository attendanceRepository;
	
	@Autowired
	WorkTimeRepository workTimeRepository;
	
	@Autowired
	TimeTableService timeTableService;
	
	@Autowired
	SalaryInformationRepository salaryInformationRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	
	//Get Staff Details
	public Object getStaff() {
		return userRepository.findUserIdStaffIdNameRoleStatusByRoleNot(5);
	}
	
	public Integer markStaffAttendance(Integer staffId) {
		if(staffRepository.existsById(staffId)) {
			Staff staffMemeber = staffRepository.findByStaffId(staffId);
			LocalDate currentDate = timeTableService.getLocalCurrentDate();
			LocalTime currentTime = timeTableService.getLocalCurrentTime();
				
			Attendance object = attendanceRepository.findByStaffId(staffId,currentDate);
			if(object != null) {
				if(object.getLeaveTime() == null) {//Only 2 Finger print per day.otherwise return 0
					//Long timeGap = ChronoUnit.HOURS.between(object.getFrom(),currentTime);
					
					object.setLeaveTime(currentTime);
					attendanceRepository.save(object);
					return 1;//success
					
				}else {
					return 0;
				}
			}else {
				Attendance attendance = new Attendance();
				attendance.setDate(currentDate);
				attendance.setComeTime(currentTime);
				attendance.setStaffId(staffMemeber);
				
				attendanceRepository.save(attendance);
				
				return 1;
			}
		}
		return null;
	}
	
	public List<SalaryInformation> getStaffSalaryInforamtion(){
		return salaryInformationRepository.findAll();
	}
	
	public SalaryInformation getStaffSalaryInformation(Integer salaryInformationId) {
		if(salaryInformationRepository.existsById(salaryInformationId)) {
			return salaryInformationRepository.findBySalaryInformationId(salaryInformationId);
		}
		return null;
	}
	
	public String addStaffSalaryInformation(SalaryInformation salaryInformation) {
		
		if(salaryInformation!=null) {
			Integer userId= salaryInformation.getAdminId().getUserId().getUserId();
			if(userId!=null) {
				Admin admin = adminRepository.findByUserId(userRepository.findByUserId(userId));
				if(admin!=null) {
					salaryInformation.setAdminId(admin);
					
					//isNotExist SalaryInformation 
					SalaryInformation object = salaryInformationRepository.findByStaffType(salaryInformation.getStaffType());
					if(object==null) {
						salaryInformationRepository.save(salaryInformation);
						return "success";
					}
				}
			}
		}
		return null;
	}
	
	public String updateStaffSalaryInformation(SalaryInformation salaryInformation) {
		if(salaryInformation!=null && salaryInformation.getSalaryInformationId()!=null && salaryInformationRepository.existsById(salaryInformation.getSalaryInformationId())) {
			salaryInformationRepository.save(salaryInformation);
			return "success";
		}
		return null;
	}
	
	public String deleteStaffSalaryInformation(Integer salaryInformationId) {
		if(salaryInformationRepository.existsById(salaryInformationId)) {
			SalaryInformation object = salaryInformationRepository.findBySalaryInformationId(salaryInformationId);
			salaryInformationRepository.delete(object);
			return "success";
		}
		return null;
	}
	
}
