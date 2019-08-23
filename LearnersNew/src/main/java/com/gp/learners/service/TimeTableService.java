package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.TimeSlot;
import com.gp.learners.repositories.TimeSlotRepositroy;

@Service
public class TimeTableService {
	
	@Autowired
	TimeSlotRepositroy timeSlotRepository;
	
	public List<TimeSlot> getTimeSlotList(){
		List<TimeSlot> timeSlotList=new ArrayList<TimeSlot>();
		if(timeSlotRepository.findAll() != null) {
			timeSlotList = timeSlotRepository.findAll();
		}
		return timeSlotList;
	}
	
	public String updateTimeSlot(TimeSlot timeSlot) {
		if(timeSlot.getTimeSlotId() != null && timeSlot.getTimeSlotId()>0) {
			if(timeSlotRepository.existsById(timeSlot.getTimeSlotId())) {
				timeSlotRepository.save(timeSlot);
				return "success";
			}
		}
		return "notsuccess";
	}
	
	public String addTimeSlot(TimeSlot timeSlot) {
		if(notExistFromAndTo(timeSlot)) {
			timeSlotRepository.save(timeSlot);
			return "success";
		}
		return "notsuccess";
	}
	
	public String deleteTimeSlot(Integer timeSlotId) {
		if(timeSlotId != null && timeSlotId>0) {
			if(timeSlotRepository.existsById(timeSlotId)) {
				timeSlotRepository.deleteById(timeSlotId);
				return "success";
			}
		}
		return "notsuccess";
	}
	
	
	//Helping Function
	
	//check whether record exist or not in the database
	private Boolean notExistFromAndTo(TimeSlot timeSlot) {
		TimeSlot object=timeSlotRepository.findByStartTimeandFinishTime(timeSlot.getStartTime(),timeSlot.getFinishTime());
		if(object != null) {
			return false;
		}
		return true;
	}
}
