package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.mapObject.StudentPackageMap;
import com.gp.learners.entities.mapObject.StudentPackageMapWrapper;
import com.gp.learners.repositories.CourseFeeRepository;
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
	
	@Autowired
	CourseFeeRepository courseFee;

	public Boolean register(Map<String, String> data) {
		return false;
	}

	// give student followed packagesId
	public List<Integer> packageListId(Integer studentId) {

		List<Integer> list = new ArrayList<Integer>();// error list

		Student student = studentRepository.getStudentId(studentId);
		if (student != null) {
			List<Integer> packId = studentPackageRepository.findByStudentId(student);
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
	
	//give student following packages
	public List<Package> packageList(Integer studentId){
		List<Package> packages=new ArrayList<Package>();
		if(studentId != null) {
			if(studentRepository.existsById(studentId)) {
				Student student=getStudent(studentId);
				if(existStudentId(student)) {//check student record has in the studentPackage table
					List<Integer> packageId=studentPackageRepository.findByStudentId(student);
					for( Integer i : packageId) {
						packages.add(packageRepository.findByPackageId(i));
					}
					return packages;
					
				}
			}
		}
		return packages;
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
	
	
	//Delete Student's package details from the db
	public String packageDelete(Integer stuId,Integer pacId) {
		if(stuId != null && pacId != null) {
			if(studentRepository.existsById(stuId) && packageRepository.existsById(pacId)) {//check whether student and package data represent the relevant tables
				if(!notExistStudentIdAndPackageId(getStudent(stuId), getPackage(pacId))) {//if relevant record exist in the studentPackage table
					
					//delete the studentPackage data(when delete studentpackage data relevant 'course fee' also deleted
					studentPackageRepository.deleteById(getStudentPackageId(stuId, pacId));
					return "success";
				}
			}
		}
		return "error";
	}
	
	//Get Student Course Fees Details
	public List<Object> courseFeeList(Integer studentId,Integer packageId){
		List<Object> courseFees=new ArrayList<Object>();
		if(studentId != null && packageId != null) {
			if(studentRepository.existsById(studentId) && packageRepository.existsById(packageId)) {//check whether student's and package's record has or not
				if(!notExistStudentIdAndPackageId(getStudent(studentId), getPackage(packageId))) {//if record exist in the studentPackage table
					
					Integer studentPackageId=getStudentPackageId(studentId, packageId);
					StudentPackage studentPackage=studentPackageRepository.findByStudentPackageId(studentPackageId);
					courseFees =courseFee.findByStudentPackageId(studentPackage);
					return courseFees;
				}
			}
		}
		return courseFees;
	}
	
	
	//Helping Function
	
	//if student and package exist the student Package table return false
	private Boolean notExistStudentIdAndPackageId(Student studentObject,Package packageObject) {
		StudentPackage object=studentPackageRepository.findByStudentIdAndPackageId(studentObject, packageObject);
		if(object != null) {
			return false;
		}
		return true;
	}
	
	//if student exist in the studentpackage table return true
	private Boolean existStudentId(Student student) {
		List<Integer> object=studentPackageRepository.findByStudentId(student);
		if(object != null) {
			return true;
		}
		return false;
	}
	
	//
	private Integer getStudentPackageId(Integer studentId,Integer packageId) {
		StudentPackage object =studentPackageRepository.findByStudentIdAndPackageId(getStudent(studentId), getPackage(packageId));
		return object.getStudentPackageId();
	}
	
	//get student object from the student table
	private Student getStudent(Integer studentId) {
		return studentRepository.findByStudentId(studentId);
	}
	
	//get package object from the table
	private Package getPackage(Integer packageId) {
		return packageRepository.findByPackageId(packageId);
	}
	
}
