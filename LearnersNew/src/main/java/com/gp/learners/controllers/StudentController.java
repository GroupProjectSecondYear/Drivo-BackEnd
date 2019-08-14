package com.gp.learners.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.mapObject.StudentPackageMapWrapper;
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
		System.out.println(student);
		return studentRepository.save(student);
	}
	
	@GetMapping("/students")
	public List<Student> studetsList(){
		return studentRepository.findAll();
	}
	
	
	//get student following package(output-->packId,Title)
	@GetMapping("student/package/{id}")
	public List<Integer> studentPakage(@PathVariable("id") Integer id) {
		return studentService.packageList(id);
	}
	
	
	//add student package details to the database
	@PostMapping("student/package/{id}")
	public Object studentPackageAdd(@PathVariable("id") Integer id,@RequestBody StudentPackageMapWrapper object) {
		
		return studentService.packageAdd(id,object);
		
	}
	
}
