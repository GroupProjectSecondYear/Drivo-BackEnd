package com.gp.learners.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.YearUpdate;
import com.gp.learners.repositories.AdminRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.repositories.YearUpdateRepository;

@Service
public class SystemUpdateService {
	
	@Autowired
	TimeTableService timeTableService;
	
	@Autowired
	YearUpdateRepository yearUpdateRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	
	//check Updates
	public void checkUpdate() {
		LocalDate currentDate = timeTableService.getLocalCurrentDate();
		Integer currentYear = currentDate.getYear();
		Integer currentMonth = currentDate.getMonthValue();
		Integer currentDay = currentDate.getDayOfMonth();
		
		YearUpdate dbYearRecord = yearUpdateRepository.getLastRecord();
		
		if(currentMonth==12) {
			if(currentDay>=1) {
				String message = "System database will going to update Jan 1.so please handle all payment before Jan 1";
				dbYearRecord.setMessage(message);
				yearUpdateRepository.save(dbYearRecord);
				
			}
		}
		
		if(currentYear!=dbYearRecord.getUpdateYear()) {
			if(dbYearRecord.getSystemUpdateStatus()!=1) {//update not complete yet
				
				//1.Get db instance and save it
				
				//2.Update the db
				//i)Update Salary Table
				//ii)Update Fuel Table
				//iii)Staff Leave Table
				//iv)Exam Result
				//v)Attendance Table
				
				//update year_update record
			}
		}
		
		
		if(currentMonth==1 && currentDay==31) {//update december staff payments
			
		}
	}
	
	//getUpdate Message
	public YearUpdate getUpdateMessage() {
		return yearUpdateRepository.getLastRecord();
	}
}
