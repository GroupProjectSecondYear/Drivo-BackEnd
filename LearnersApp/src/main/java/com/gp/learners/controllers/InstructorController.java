package com.gp.learners.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.mapObject.LessonAssingStudentMap;
import com.gp.learners.entities.mapObject.LessonMap;
import com.gp.learners.entities.mapObject.StudentPractricalChartDataMap;
import com.gp.learners.service.InstructorService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class InstructorController {

	@Autowired
	InstructorService instructorService;

	@GetMapping("/instructor/lesson/{userId}")
	public ResponseEntity<List<LessonMap>> getInstructorLesson(@PathVariable("userId") Integer userId) {
		List<LessonMap> list = instructorService.getInstructorLesson(userId);
		if (list != null) {
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/instructor/assignstudent/{userId}/{lessonId}")
	public ResponseEntity<List<LessonAssingStudentMap>> getAssignStudent(@PathVariable("userId") Integer userId,
			@PathVariable("lessonId") Integer lessonId) {
		List<LessonAssingStudentMap> studentList = instructorService.getAssignStudent(userId, lessonId);
		if (studentList != null) {
			return ResponseEntity.ok(studentList);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/instructor/lesson/date/{userId}/{lessonId}")
	public ResponseEntity<LocalDate> getLessonDate(@PathVariable("userId") Integer userId,
			@PathVariable("lessonId") Integer lessonId) {
		LocalDate date = instructorService.getLessonDate(userId, lessonId);
		if (date != null) {
			return ResponseEntity.ok(date);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/instructor/student/lesson/mark/{studentLessonId}/{mark}")
	public ResponseEntity<Void> markStudentLesson(@PathVariable("studentLessonId") Integer studentLessonId,
			@PathVariable("mark") Integer mark) {
		Integer reply = instructorService.markStudentLesson(studentLessonId, mark);
		if (reply != -1) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/instructor/student/practrical/lesson/{studentLessonId}")
	public ResponseEntity<StudentPractricalChartDataMap> getPractricalLessonChartStudentData(
			@PathVariable("studentLessonId") Integer studentLessonId) {
		StudentPractricalChartDataMap object = instructorService.getPractricalLessonChartStudentData(studentLessonId);
		if (object != null) {
			return ResponseEntity.ok(object);
		}
		return ResponseEntity.notFound().build();
	}

	// get Instructor list
	@GetMapping("/instructors/{status}")
	public List<Instructor> getInstructorList(@PathVariable("status") Integer status) {
		System.out.println("In Instructor Controller");
		List<Instructor> InsList = instructorService.getInstructorList(status);
		System.out.println(InsList.get(0).getLicence());
		return InsList;
	}


	@GetMapping("instructor/{instructorId}")
	public ResponseEntity<Instructor> getInstructor(@PathVariable("instructorId") Integer instructorId) {
		Instructor instructor = instructorService.getInstructorByID(instructorId);
		if (instructor.getInstructorId() != null) {
			return ResponseEntity.ok(instructor);
		}
		return ResponseEntity.noContent().build();
	}

	// update Instructor Details
	@PutMapping("instructor/update")
	public ResponseEntity<Integer> updateInstructor(@Valid @RequestBody Instructor object) {
		Integer reply = instructorService.updateInstructor(object);
		if (reply != null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}

	// register Instructor
	@PostMapping("/instructor/register")
	public Integer InstructorRegister(@RequestBody Instructor instructor) {
		return instructorService.instructorRegister(instructor);
	}

	// get Instructor by Email
	@GetMapping("instructor/getbyEmail/{email}")
	public ResponseEntity<Instructor> getInstructorbyEmail(@PathVariable("email") String email) {
		System.out.println("getByEmail" + email);
		Instructor instructor = instructorService.getInstructorbyEmail(email);
		System.out.println("getByEmail" + instructor);
		if (instructor.getInstructorId() != null) {
			return ResponseEntity.ok(instructor);
		}
		return ResponseEntity.noContent().build();
	}

	// deactivate Instructor Profile
	@PutMapping("instructor/deactivate/{instructorId}")
	public ResponseEntity<Integer> deactivateInstructor(@PathVariable("instructorId") Integer instructorId) {
		Integer reply = instructorService.deactivateInstructor(instructorId);
		if (reply != null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
	@PutMapping("instructor/activate/account/{instructorId}")
	public ResponseEntity<Integer> activateInstructorAccount(@PathVariable("instructorId") Integer instructorId){
		Integer reply = instructorService.activateInstructorAccount(instructorId);
		System.out.println("Ins Activation");
		
		if(reply!=null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}

}
