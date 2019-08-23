package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.TimeSlot;
import com.gp.learners.service.TimeTableService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class TimeTableController {
	
	@Autowired
	private TimeTableService timeTableService;
	
	@GetMapping("/timetable/timeslots")
	public List<TimeSlot> getTimeSlotList(){
		return timeTableService.getTimeSlotList();
	}
	
	@PutMapping("/timetable/timeslot")
	public ResponseEntity<Void> updateTimeSlot(@RequestBody TimeSlot object){
		System.out.println(object);
		String reply=timeTableService.updateTimeSlot(object);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/timetable/timeslot")
	public ResponseEntity<Void> addTimeSlot(@RequestBody TimeSlot object){
		String reply=timeTableService.addTimeSlot(object);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/timetable/timeslot/{id}")
	public ResponseEntity<Void> deleteTimeSlot(@PathVariable("id") Integer timeSlotId){
		String reply=timeTableService.deleteTimeSlot(timeSlotId);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
