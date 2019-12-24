package com.gp.learners.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.mapObject.LessonDayFeedbackChartDataMap;
import com.gp.learners.service.LessonBookingService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class LessonBookingController {
	
	@Autowired
	private LessonBookingService lessonBookingService;
	
	@GetMapping("/lessonbooking/studentpackage/{id}")
	public ResponseEntity<List<StudentPackage>> getStudentPackageData(@PathVariable("id") Integer userId){
		List<StudentPackage> studentPackageList=lessonBookingService.getStudentPackageData(userId);
		if(studentPackageList != null) {
			return ResponseEntity.ok(studentPackageList);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/lessonbooking/trialdate/{id}")
	public ResponseEntity<LocalDate> getTrialDate(@PathVariable("id") Integer userId){
		Student student=lessonBookingService.getTrialDate(userId);
		if(student != null) {
			return ResponseEntity.ok(student.getTrialDate());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/lessonbooking/{date}/{studentPackageId}/{timeSlotId}")
	public ResponseEntity<Lesson> getAvailableLesson(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,@PathVariable("studentPackageId") Integer studentPackageId,@PathVariable("timeSlotId") Integer timeSlotId) {
		Lesson lesson = lessonBookingService.getAvailableLesson(date,studentPackageId,timeSlotId);
		if(lesson.getLessonId() != null) {
			return ResponseEntity.ok(lesson);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("lessonbooking/{lessonId}/{studentPackageId}/{date}")
	public ResponseEntity<Integer> saveBooking(@PathVariable("lessonId") Integer lessonId,@PathVariable("studentPackageId") Integer studentPackageId,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date){
		Integer reply=lessonBookingService.saveBooking(lessonId,studentPackageId,date);
		if(reply!= -1) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.badRequest().build();
	}
	
	//get student following packages
	@GetMapping("lessonbooking/studentpackages/{userId}")
	public List<Package> getStudentPackages(@PathVariable("userId") Integer userId){
		return lessonBookingService.getStudentPackages(userId);
	}
	
	//get student's book lesson details
	@GetMapping("lessonbooking/booklessons/{userId}/{packageId}")
	public ResponseEntity<List<StudentLesson>> getBookLessonDetails(@PathVariable("userId") Integer userId,@PathVariable("packageId") Integer packageId){
		List<StudentLesson> studentLesson = lessonBookingService.getBookLessonDetails(userId,packageId);
		
		if(studentLesson != null && studentLesson.size()>0) {
			if(studentLesson.get(0).getStudentLessonId() == -1) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.ok(studentLesson);
	}
	
	@DeleteMapping("lessonbooking/cancelbooking/{id}")
	public ResponseEntity<Void> cancelBooking(@PathVariable("id") Integer studentLessonId){
		String reply=lessonBookingService.cancelBooking(studentLessonId);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("lessonbooking/lessonday/feedback/{userId}/{packageId}/{day1}/{time1}/{day2}/{time2}")
	public ResponseEntity<List<Integer>> lessonDayFeedback(@PathVariable("userId") Integer userId,@PathVariable("packageId") Integer packageId,
												  @PathVariable("day1") Integer day1,@PathVariable("time1") Integer time1,
												  @PathVariable("day2") Integer day2,@PathVariable("time2") Integer time2){
		
		List<Integer> reply = lessonBookingService.lessonDayFeedback(userId,packageId,day1,time1,day2,time2);
		return ResponseEntity.ok(reply);
	}
	
	@GetMapping("lessonbooking/lessonday/feedback/chart/{packageId}/{transmission}")
	public ResponseEntity<List<LessonDayFeedbackChartDataMap>> lessonDayFeedbackChartData(@PathVariable("packageId") Integer packageId,@PathVariable("transmission") Integer transmission) {

		List<LessonDayFeedbackChartDataMap> list = lessonBookingService.lessonDayFeedbackChartData(packageId,transmission);
		if(list!=null) {
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("lessonbooking/course/payment/{studentPackageId}")
	public ResponseEntity<Integer> checkCoursePayment(@PathVariable("studentPackageId") Integer studentPackageId){
		Integer reply = lessonBookingService.checkCoursePayment(studentPackageId);
		if(reply!=null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
}
