package com.gp.learners.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.LessonRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.TimeSlotRepositroy;
import com.gp.learners.repositories.UserRepository;

@Service
public class LessonBookingService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	StudentPackageRepository studentPackageRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TimeSlotRepositroy timeSlotRepository;
	
	//get student following lesson details
	public List<StudentPackage> getStudentPackageData(Integer userId){
		
		List<StudentPackage> studentPackageList = new ArrayList<StudentPackage>();
		if(userRepository.existsById(userId)) {
			
			Student student=getStudent(userId);
			if(student.getStudentId() != null) {
				studentPackageList = studentPackageRepository.packageListfindByStudentId(student);
				
				Student object=new Student(); 
				for (StudentPackage studentPackage : studentPackageList) {
					studentPackage.setStudentId(object);
				}
				return studentPackageList;
			}
			
		}
		return studentPackageList;
	}
	
	//get Student Trial Date
	public Student getTrialDate(Integer userId) {
		if(userRepository.existsById(userId)) {
			Student student = getStudent(userId);
			return student;
		}
		return new Student();
	}
	
	public String getAvailableLesson(LocalDate date,Integer studentPackageId,Integer timeSlotId) {
		
		/*In the db store 
		0->Sunday
		1->Monday
		6->Saturday
		
		But getDayOfWeek function give as
		1-> Monday
		7->Sunday
		
		so dayId==7 convert to 0
		*/
		Integer dayId=date.getDayOfWeek().getValue();
		if(dayId==7) {
			dayId=0;
		}
		
		StudentPackage studentPackage = studentPackageRepository.findByStudentPackageId(studentPackageId);
		TimeSlot timeSlot = timeSlotRepository.findByTimeSlotId(timeSlotId);
		
		List<Lesson> lessonList = lessonRepository.findLesson(dayId,studentPackage.getPackageId(),timeSlot);
		
		//find available lesson
		for (Lesson lesson : lessonList) {
			
		}
		
		return "";
	}
	
	//Helping Functions
	private Student getStudent(Integer userId) {
		Student student=new Student();
		Integer studentId=studentRepository.findByUserId(userRepository.findByUserId(userId));
		if(studentId != null) {
			student=studentRepository.findByStudentId(studentId);
		}
		return student;
	}
}
