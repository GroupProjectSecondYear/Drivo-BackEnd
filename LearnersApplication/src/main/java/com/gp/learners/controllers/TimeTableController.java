package com.gp.learners.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Path;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.mapObject.InstructorMap;
import com.gp.learners.entities.mapObject.LessonDistributionMap;
import com.gp.learners.entities.mapObject.LessonMap;
import com.gp.learners.entities.mapObject.PackageAnalysisDataMap;
import com.gp.learners.entities.mapObject.StudentAttendanceWeeksMap;
import com.gp.learners.service.TimeTableService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class TimeTableController {
	
	@Autowired
	private TimeTableService timeTableService;
	
	
	//1)Time Slot Functions
	
	@GetMapping("/timetable/timeslots")
	public List<TimeSlot> getTimeSlotList(){
		return timeTableService.getTimeSlotList();
	}
	
	@PutMapping("/timetable/timeslot")
	public ResponseEntity<Void> updateTimeSlot(@RequestBody TimeSlot object){
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
	public ResponseEntity<Integer> deleteTimeSlot(@PathVariable("id") Integer timeSlotId){
		Integer reply=timeTableService.deleteTimeSlot(timeSlotId);
		if(reply != null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
	
	//2)Path functions
	
	@PostMapping("/timetable/path")
	public ResponseEntity<Integer> addPath(@Valid @RequestBody Path object){
		Integer reply=timeTableService.addPath(object);
		if(reply>0) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("timetable/path/{pathId}")
	public ResponseEntity<Void> deletePath(@PathVariable Integer pathId){
		String reply=timeTableService.deletePath(pathId);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/timetable/path")
	public List<Path> getPathList(){
		return timeTableService.getPathList();
	}
	
	@PutMapping("timetable/path")
	public ResponseEntity<Integer> updatePath(@Valid @RequestBody Path object){
		Integer reply=timeTableService.updatePath(object);
		if(reply>0) {
			return ResponseEntity.ok(reply);
		}
	
		return ResponseEntity.notFound().build();
	}
	
	//subPath
	@PostMapping("/timetable/subpath/{pathId}")
	public ResponseEntity<Void> addSubPath(@PathVariable("pathId") Integer pathId,@RequestBody ArrayList<String> subPaths){
		String reply=timeTableService.addSubPaths(pathId,subPaths,1);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/timetable/subpath/{pathId}")
	public List<String> getSubPathList(@PathVariable("pathId") Integer pathId){
		return timeTableService.getSubPathList(pathId);
	}
	
	@PutMapping("/timetable/subpath/{pathId}")
	public ResponseEntity<Void> updateSubPath(@PathVariable("pathId") Integer pathId,@RequestBody ArrayList<String> subPaths){
		String reply=timeTableService.addSubPaths(pathId,subPaths,2);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	//3)Instructor functions
	@GetMapping("/timetable/instructors/{dayId}/{pacId}/{timeSlotId}/{pathId}/{transmission}")
	public List<InstructorMap> getRelevantInstructors(@PathVariable("dayId") Integer dayId,
										 @PathVariable("pacId") Integer packageId ,
										 @PathVariable("timeSlotId") Integer timeSlotId,
										 @PathVariable("pathId") Integer pathId,
										 @PathVariable("transmission") Integer transmission){
		return timeTableService.getRelevantInstructors(dayId,packageId,timeSlotId,pathId,transmission);

	}
	
	//4)Lesson Functions
	@PostMapping("/timetable/lesson/{dayId}/{pacId}/{timeSlotId}/{pathId}/{transmission}/{insId}/{numStu}")
	public ResponseEntity<Void> addLesson(@PathVariable("dayId") Integer dayId	,
			 @PathVariable("pacId") Integer packageId ,
			 @PathVariable("timeSlotId") Integer timeSlotId,
			 @PathVariable("pathId") Integer pathId,
			 @PathVariable("transmission") Integer transmission,
			 @PathVariable("insId") Integer instructorId,
			 @PathVariable("numStu") Integer numStudent)throws RestClientException{
		
		
		String reply=timeTableService.addLesson(dayId,packageId,timeSlotId,pathId,transmission,instructorId,numStudent);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	//type 0:Deactivated Lesson  / 1:Activated Lesson
	@GetMapping("/timetable/lessons/{type}")
	public List<LessonMap> getLessons(@PathVariable("type") Integer type) {
		return timeTableService.getLessonList(type,-1);
	}
	
	@GetMapping("/timetable/lesson/{id}")
	public ResponseEntity<Lesson> getLesson(@PathVariable("id") Integer lessonId) {
		Lesson lesson=timeTableService.getLesson(lessonId);
		if(lesson.getLessonId() != null) {
			return ResponseEntity.ok(lesson);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/timetable/lesson/{id}")
	public ResponseEntity<Integer> deleteLesson(@PathVariable("id")Integer lessonId){
		
		Integer reply=timeTableService.deleteLesson(lessonId);
		if(reply == -1) {
			System.out.println("no");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reply);
	}
	
	@PutMapping("/timetable/lesson/deactivate/{id}")
	public ResponseEntity<Void> deactivateLesson(@PathVariable("id") Integer lessonId){
		String reply=timeTableService.lessonDeactivate(lessonId);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/timetable/lesson/activate/{id}")
	public ResponseEntity<Integer> activateLesson(@PathVariable("id") Integer lessonId){
		Integer reply=timeTableService.lessonActivate(lessonId);
		if(reply==-1) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reply);
	}
	
	@PutMapping("/timetable/lesson/{lessonId}/{type}/{dayId}/{timeSlotId}/{pathId}/{insId}/{numStu}")
	public ResponseEntity<Void> updateLesson(@PathVariable("lessonId") Integer lessonId	,
			 @PathVariable("type") Integer type ,
			 @PathVariable("dayId") Integer dayId,
			 @PathVariable("timeSlotId") Integer timeSlotId,
			 @PathVariable("pathId") Integer pathId,
			 @PathVariable("insId") Integer instructorId,
			 @PathVariable("numStu") Integer numStudent)throws RestClientException{
		
		String reply=timeTableService.updateLesson(lessonId,type,dayId,timeSlotId,pathId,instructorId,numStudent);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("timetable/lesson/week/{packageId}/{type}")
	public ResponseEntity<List<LessonDistributionMap>> getLessonDistributionDetails(@PathVariable("packageId") Integer packageId,@PathVariable("type") Integer type){
		List<LessonDistributionMap> lessonDistribution = timeTableService.getLessonDistributionDetails(packageId,type);
		if(lessonDistribution != null && lessonDistribution.size()>0) {
			if(lessonDistribution.get(0).getDay() == -1) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.ok(lessonDistribution);
	}
	
	@GetMapping("timetable/lesson/details/{packageId}/{transmission}")
	public List<PackageAnalysisDataMap> getLessonsByPackageId(@PathVariable("packageId") Integer packageId ,@PathVariable("transmission") Integer transmission){
		return timeTableService.getLessonsByPackageId(packageId,transmission);
	}
	
	@GetMapping("timetable/lesson/timeslot/{packageId}/{transmission}")
	public List<TimeSlot> getLessonTimeSlotByPackageId(@PathVariable("packageId") Integer packageId ,@PathVariable("transmission") Integer transmission){
		return timeTableService.getLessonTimeSlotByPackageId(packageId,transmission);
	}
	
	@GetMapping("timetable/lesson/isanylesson/{packageId}/{transmission}")
	public ResponseEntity<Boolean> isAnyLesson(@PathVariable("packageId") Integer packageId,@PathVariable("transmission") Integer transmission){
		Integer reply = timeTableService.isAnyLesson(packageId,transmission);
		if(reply == 0) {
			return ResponseEntity.ok(false);
		}
		if(reply == 1) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	//get Student attendance week by week(12 weeks)
	//time 1 --> Past , 2--> Future
	@GetMapping("timetable/lesson/student/attendance/{lessonId}/{time}")
	public ResponseEntity<List<StudentAttendanceWeeksMap>> getStudentAttendance(@PathVariable("lessonId") Integer lessonId,@PathVariable("time") Integer time) {
		List<StudentAttendanceWeeksMap> list= new ArrayList<StudentAttendanceWeeksMap>();
		list = null;
		if(time==1) {
			 list = timeTableService.getStudentAttendancePast(lessonId);
		}else {
			 list = timeTableService.getStudentAttendanceFuture(lessonId);
		}
	
		if(list != null) {
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.notFound().build();	
	}
	
	@GetMapping("timetable/lesson/date/{lessonId}")
	public ResponseEntity<LocalDate> lessonPublishDate(@PathVariable("lessonId") Integer lessonId){
		LocalDate date = timeTableService.getLessonPublishDate(lessonId);
		if(date != null) {
			return ResponseEntity.ok(date);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("timetable/timeslot/date/{packageId}/{transmission}/{date}")
	public ResponseEntity<List<TimeSlot>> getTimeSlotListAccordingToDateAndPackage(@PathVariable("packageId") Integer packageId,@PathVariable("transmission") Integer transmission, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@PathVariable("date") LocalDate date){
		List<TimeSlot> timeSlot = timeTableService.getTimeSlotListAccordingToDateAndPackage(packageId, transmission, date);
		if(timeSlot!=null) {
			return ResponseEntity.ok(timeSlot);
		}
		return ResponseEntity.notFound().build();
	}
		
	
}
