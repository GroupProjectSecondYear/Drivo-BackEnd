package com.gp.learners.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.mapObject.StudentPackageMap;
import com.gp.learners.entities.mapObject.StudentPackageMapWrapper;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;
import java.time.LocalDate;

@Service
public class StudentService {

	@Autowired
	StudentPackageRepository studentPackageRepository;

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	PackageRepository packageRepository;

	public Boolean register(Map<String, String> data) {
		return false;
	}

	// give student followed packages
	public List<Integer> packageList(Integer studentId) {

		List<Integer> list = new ArrayList<Integer>();// error list

		Student student = studentRepository.getStudentId(studentId);
		if (student != null) {
			List<Integer> packId = studentPackageRepository.getStudentId(student);
			if (packId != null && packId.size() > 0) {
				return packId;
			}
			list.add(-2);// if no any packages that student follow
			return list;
		}

		// if no such a student
		list.add(-1);
		return list;
	}

	// Add Student following package details to the db
	public Object packageAdd(Integer id, StudentPackageMapWrapper object) {
		
		StudentPackage studentPackageObject=new StudentPackage();
		//insert reply 
		ArrayList<Integer> reply=new ArrayList<Integer>();
		
		
		for (StudentPackageMap studentPackage : object.getStudentPackageMap()) {
			if( (studentPackage != null) && (studentPackage.getPackageId() != null) && (studentPackage.getTransmission()!=null)) {
				
				//check whether (student && package)exist or not
				if(studentRepository.existsById(id) && packageRepository.existsById(studentPackage.getPackageId())) {
					
					//check if already student register this course(Package) or not
					//if not register for course
					if(notExistStudentIdAndPackageId(studentRepository.getStudentId(id),packageRepository.findByPackageId(studentPackage.getPackageId()))) {
						
						//create studentpackage object
						studentPackageObject.setJoinDate(LocalDate.now());
						studentPackageObject.setPackageId(packageRepository.findByPackageId(studentPackage.getPackageId()));
						studentPackageObject.setStudentId(studentRepository.getStudentId(id));
						studentPackageObject.setTransmission(studentPackage.getTransmission());
						
						//save studentpackage object to database
						studentPackageRepository.save(studentPackageObject);
						reply.add(studentPackage.getPackageId());
					}
					
				}
			}
		}
		return reply;
		
	}
	
	private Boolean notExistStudentIdAndPackageId(Student studentObject,Package packageObject) {
		StudentPackage object=studentPackageRepository.findByStudentIdAndPackageId(studentObject, packageObject);
		if(object != null) {
			return false;
		}
		return true;
	}

}
