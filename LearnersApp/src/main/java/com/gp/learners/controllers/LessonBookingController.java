package com.gp.learners.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.service.LessonBookingService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class LessonBookingController {
	
	@Autowired
	private LessonBookingService lessonBookingService;
	
	@GetMapping("/lessonbooking/studentpackage/{id}")
	public ResponseEntity<List<StudentPackage>> getStudentPackageData(@PathVariable("id") Integer userId){
		List<StudentPackage> studentPackageList=lessonBookingService.getStudentPackageData(userId);
		if(studentPackageList.size()>0) {
			return ResponseEntity.ok(studentPackageList);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/lessonbooking/trialdate/{id}")
	public ResponseEntity<LocalDate> getTrialDate(@PathVariable("id") Integer userId){
		Student student=lessonBookingService.getTrialDate(userId);
		if(student.getStudentId() != null) {
			return ResponseEntity.ok(student.getTrialDate());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/lessonbooking/{date}/{studentPackageId}/{timeSlotId}")
	public String getAvailableLesson(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,@PathVariable("studentPackageId") Integer studentPackageId,@PathVariable("timeSlotId") Integer timeSlotId) {
		
		lessonBookingService.getAvailableLesson(date,studentPackageId,timeSlotId);
		
		return "";
	}
}
