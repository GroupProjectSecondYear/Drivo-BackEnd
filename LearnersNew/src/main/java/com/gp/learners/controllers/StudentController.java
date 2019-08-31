package com.gp.learners.controllers;


import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.CourseFee;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.mapObject.StudentPackageMap;
import com.gp.learners.entities.mapObject.StudentTrialMap;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.service.StudentService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/student/register")
	public Student StudentRegister(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@GetMapping("/students")
	public List<Student> getStudetsList(){
		return studentService.getStudentList();
	}
	
	
	//get student following package(output-->packId,Title)
	@GetMapping("student/package/{id}")
	public List<Integer> studentPakage(@PathVariable("id") Integer id) {
		return studentService.packageListId(id);
	}
	
	//get student following package(output-->packId,Title)
	@GetMapping("student/package/list/{id}")
	public List<Package> studentPakageList(@PathVariable("id") Integer id) {
			return studentService.packageList(id);
	}
	
	
	//add student package details to the database
	@PostMapping("student/package/{stuId}")
	public ResponseEntity<Void> studentPackageAdd(@PathVariable("stuId") Integer stuId,@RequestBody StudentPackageMap object) {
		System.out.println("stuId:"+stuId+" object:"+object);
		String reply=studentService.packageAdd(stuId, object);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//delete student package details from the database
	@DeleteMapping("student/package/{stuId}/{pacId}")
	public ResponseEntity<Void> studentPackageDelete(@PathVariable("stuId") Integer stuId,@PathVariable("pacId") Integer pacId ) {
			
		if(studentService.packageDelete(stuId,pacId).equals("success")) {
			System.out.println("ok2");
			return ResponseEntity.noContent().build();
		}
		System.out.println("Ok1");
		return ResponseEntity.notFound().build();
			
	}
	
	//student Course Fee Details(return the --> courseFeeId,amount,date,method,studentPackageId)
	@GetMapping("student/coursefees/{stuId}/{pacId}")
	public List<Object> courseFees(@PathVariable("stuId") Integer stuId,@PathVariable("pacId") Integer pacId) {
			return studentService.courseFeeList(stuId,pacId);
	}
	
	@PostMapping("student/coursefee/{studentId}/{packageId}")//Bean Validation Not done
	public ResponseEntity<Void> courseFeeAdd(@PathVariable("studentId") Integer stuId,@PathVariable("packageId") Integer pacId,@RequestBody CourseFee object) {
		
		String reply= studentService.courseFeeAdd(stuId,pacId,object);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.unprocessableEntity().build();
	}
	
	
	//get Specific student data
	@GetMapping("student/{stuId}")
	public ResponseEntity<Student> getStudent(@PathVariable("stuId") Integer studentId) {
		Student student=studentService.getStudentDetails(studentId);
		if(student.getStudentId() != null) {
			return ResponseEntity.ok(student);
		}
		return ResponseEntity.noContent().build();
	}
	
	//update student Details
	@PutMapping("student/update")
	public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student object){
		Student student=studentService.studentUpdate(object);
		if(student.getStudentId() != null) {
			return ResponseEntity.ok(student);
		}
		return ResponseEntity.notFound().build();
	}
	
	//delete Student
	@DeleteMapping("student/{stuId}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("stuId") Integer studentId){
		String reply=studentService.deleteStudent(studentId);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//getStudentId
	@GetMapping("student/id/{userId}")
	public ResponseEntity<Integer> getUserId(@PathVariable("userId") Integer id) {
		Integer studentId=studentService.getStudentId(id);
		if(studentId != (-1)) {
			return ResponseEntity.ok(studentId);
		}
		return ResponseEntity.notFound().build();
	}
	
	//getStudent Trial List
	@GetMapping("student/trial/list")
	public List<StudentTrialMap> getStudentTrialList(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
		return studentService.getStudentTrialList(date);
	}
	
	//getStudent Exam List
	@GetMapping("student/exam/list")
	public List<StudentTrialMap> getStudentExamList(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
		return studentService.getStudentExamList(date);
	}
	
	@GetMapping("student/writtenexam/result")
	public List<Double> getWrittenExamResult(){
		return studentService.getWrittenExamResult();
	}
	
	@PostMapping("student/writtenexam/result")
	public ResponseEntity<Void> submitWrittenExamResult(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
														@RequestParam("countPass") Integer countPass,
														@RequestParam("countFail") Integer countFail){
		String reply=studentService.submitWrittenExamResult(date,countPass,countFail);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("student/trialexam/result")
	public ResponseEntity<Void> submitTrialExamResult(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
														@RequestParam("countPass") Integer countPass,
														@RequestParam("countFail") Integer countFail){
		String reply=studentService.submitWrittenExamResult(date,countPass,countFail);
		if(reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
