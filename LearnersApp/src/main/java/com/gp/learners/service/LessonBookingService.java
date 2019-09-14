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
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.LessonRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentLessonRepository;
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
	
	@Autowired
	StudentLessonRepository studentLessonRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	StudentService studentService;
	
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
	
	public Lesson getAvailableLesson(LocalDate date,Integer studentPackageId,Integer timeSlotId) {
		
		Lesson object = new Lesson();
		
		if(studentPackageRepository.existsById(studentPackageId) && timeSlotRepository.existsById(timeSlotId)) {
		
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
			
			List<Lesson> lessonList = lessonRepository.findLesson(dayId,studentPackage.getPackageId(),studentPackage.getTransmission(),timeSlot);
			
			//no any lesson 
			if(lessonList.size()==0) {
				object.setLessonId(0);
				return object;
			}
			
			//find available lesson
			for (Lesson lesson : lessonList) {
				Integer numStu=lesson.getNumStu();
				Integer countOfLessonBook=studentLessonRepository.findLessonBookCount(lesson,date);
				
				//check any available space for booking
				if(numStu > countOfLessonBook) {
					return lesson;
				}
				
			}
			
			//if not find any available lesson
			object.setLessonId(-1);
			return object;
		}
		return object;
	}
	
	public Integer saveBooking(Integer lessonId,Integer studentPackageId,LocalDate date) {
		
		/*Reply:
		 * 1--> lesson booking success
		 * 0-->	no available lesson for booking (student)
		 * -1-> Requested data not represent in the tables(packageId,StudentPackageId)
		 * -2 -> Already this lesson book previously(Not perform duplicate same record)
		 */
		
		if(lessonRepository.existsById(lessonId) && studentPackageRepository.existsById(studentPackageId)) {
			
			if(notExistLessonBook(lessonId,studentPackageId,date)) {
				StudentLesson object = new StudentLesson();
				
				//check whether student have remaining lesson for booking
				StudentPackage studentPackage = studentPackageRepository.findByStudentPackageId(studentPackageId);
				Integer studentSelectedTransmission=studentPackage.getTransmission();
				Integer numOfLessonForStudent=0;
				if(studentSelectedTransmission == 1) {//Manual Lesson
					numOfLessonForStudent=studentPackage.getPackageId().getManualLes();
				}else {
					numOfLessonForStudent=studentPackage.getPackageId().getAutoLes();
				}
				
				Integer numOfLessonAlreadyBook=studentLessonRepository.findAlreadyBookLesson(studentPackage.getStudentId(),studentPackage.getPackageId());
				Integer remainingLessonForStudent;
				if(numOfLessonAlreadyBook != null) {
					remainingLessonForStudent=numOfLessonForStudent-numOfLessonAlreadyBook; 
				}else {
					remainingLessonForStudent=numOfLessonForStudent;
				}
				
				//if student has remaining lesson for booking
				if(remainingLessonForStudent > 0) {
					object.setDate(date);
					object.setStudentId(studentRepository.findByStudentId(studentPackage.getStudentId().getStudentId()));
					object.setLessonId(lessonRepository.findByLessonId(lessonId));
					object.setComplete(0);
					studentLessonRepository.save(object);
					
					return 1;
				}
				return 0;
			}
			return -2;	
		}
		return -1;
	}
	
	public List<Package> getStudentPackages(Integer userId){
		List<Package> studentPackageList=new ArrayList<Package>();
		if(userRepository.existsById(userId)) {
			
			Student student=getStudent(userId);
			studentPackageList=studentService.packageList(student.getStudentId());
		}
		return studentPackageList;
	}
	
	public List<StudentLesson> getBookLessonDetails(Integer userId,Integer packageId){
		List<StudentLesson> studentLessonList = new ArrayList<StudentLesson>();
		if(userRepository.existsById(userId) && packageRepository.existsById(packageId)) {
			
			Student student=getStudent(userId);
			if(student != null) {
				studentLessonList = studentLessonRepository.findByStudentIdAndPackageId(student,packageRepository.findByPackageId(packageId));
				return studentLessonList;
			}
		}
	
		StudentLesson object = new StudentLesson();
		object.setStudentLessonId(-1);
		studentLessonList.add(object);
		return studentLessonList;
	}
	
	public String cancelBooking(Integer studentLessonId) {
		if(studentLessonRepository.existsById(studentLessonId)) {
			studentLessonRepository.deleteById(studentLessonId);
			return "success";
		}
		return "notsuccess";
	}
	
	//Helping Functions
	private Student getStudent(Integer userId) {
		Student student=null;
		Integer studentId=studentRepository.findByUserId(userRepository.findByUserId(userId));
		if(studentId != null) {
			student=studentRepository.findByStudentId(studentId);
		}
		return student;
	}
	
	
	private Boolean notExistLessonBook(Integer lessonId,Integer studentPackageId,LocalDate date) {
		
		StudentLesson object=studentLessonRepository.findStudentIdPackageIdDate(lessonRepository.findByLessonId(lessonId),studentPackageRepository.findByStudentPackageId(studentPackageId).getStudentId(),date);
		if(object != null) {
			return false;
		}
		return true;
	}
}
