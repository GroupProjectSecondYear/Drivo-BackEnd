package com.gp.learners.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;



@Service
public class StudentService {
	
	@Autowired
	StudentPackageRepository studentPackageRepository;
	
	@Autowired 
	StudentRepository studentRepository;
	
	public Boolean register(Map<String, String> data) {
		return false;
	}
	
	
	//give student followed packages
	public Object packageList(Integer id){
		
		if(studentRepository.existsById(id)) {
			//System.out.println(studentPackageRepository.getStudentDetailsId(id));
		}
		
		
		return null;
	}
}
