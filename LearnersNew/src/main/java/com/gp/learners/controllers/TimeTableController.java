package com.gp.learners.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.gp.learners.entities.Path;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.mapObject.InstructorMap;
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
}
