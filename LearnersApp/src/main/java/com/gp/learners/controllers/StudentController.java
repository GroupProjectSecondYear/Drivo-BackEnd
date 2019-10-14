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
	public Integer StudentRegister(@RequestBody Student student) {//0:Student already registered ,1:Register success
		return studentService.studentRegister(student);
	}
	
	@GetMapping("/students/{status}")
	public List<Student> getStudetsList(@PathVariable("status") Integer status){
		return studentService.getStudentList(status);
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
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
			
	}
	
	//student Course Fee Details(return the --> courseFeeId,amount,date,method,studentPackageId)
	@GetMapping("student/coursefees/{stuId}/{pacId}")
	public List<Object> courseFees(@PathVariable("stuId") Integer stuId,@PathVariable("pacId") Integer pacId) {
			return studentService.courseFeeList(stuId,pacId);
	}
	
	@PostMapping("student/coursefee/{studentId}/{packageId}")//Bean Validation Not done
	public ResponseEntity<Integer> courseFeeAdd(@PathVariable("studentId") Integer stuId,@PathVariable("packageId") Integer pacId,@RequestBody CourseFee object) {
		
		Integer reply= studentService.courseFeeAdd(stuId,pacId,object);
		if(reply != -1) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.unprocessableEntity().build();
	}
	
	
	//get Specific student data
	@GetMapping("student/{stuId}")
	public ResponseEntity<Student> getStudent(@PathVariable("stuId") Integer studentId) {
		Student student=studentService.getStudentDetails(studentId);
		if(student != null) {
			return ResponseEntity.ok(student);
		}
		return ResponseEntity.noContent().build();
	}
	
	//update student Details
	@PutMapping("student/update")
	public ResponseEntity<Integer> updateStudent(@Valid @RequestBody Student object){
		Integer reply=studentService.studentUpdate(object);
		if(reply != null) {
			return ResponseEntity.ok(reply);
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
	
	@GetMapping("student/user/{id}")
	public ResponseEntity<Student> getStudentData(@PathVariable("id") Integer userId) {
		
		Student student=studentService.getStudentData(userId);
		if(student.getStudentId() != null) {
			return ResponseEntity.ok(student);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("student/activate/account/{studentId}")
	public ResponseEntity<Integer> activateStudentAccount(@PathVariable("studentId") Integer studentId){
		Integer reply = studentService.activateStudentAccount(studentId);
		if(reply!=null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("student/clear/payment/{studentId}")
	public ResponseEntity<Void> clearStudentPreviousPayment(@PathVariable("studentId") Integer studentId){
		String reply = studentService.clearStudentPreviousPayment(studentId);
		if(reply!=null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("student/payment/notcomplete")
	public List<Student> getpaymentNotCompleteStudent(){
		return studentService.getpaymentNotCompleteStudent();
	}
	
}
