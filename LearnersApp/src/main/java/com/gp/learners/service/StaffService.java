package com.gp.learners.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.WorkTime;
import com.gp.learners.repositories.AttendanceRepository;
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
}
