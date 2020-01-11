package com.gp.learners.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.entities.mapObject.LessonAssingStudentMap;
import com.gp.learners.entities.mapObject.LessonMap;
import com.gp.learners.entities.mapObject.StudentPractricalChartDataMap;
import com.gp.learners.repositories.InstructorRepository;
import com.gp.learners.repositories.LessonRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.StudentLessonRepository;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.UserRepository;

import ch.qos.logback.core.util.Duration;

@Service
public class InstructorService {
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	TimeTableService timeTableService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	@Autowired
	InstructorRepository instructorRepository;
	
	@Autowired
	StudentLessonRepository studentLessonRepository;
	
	
	//get Instructor's lesson list
	public List<LessonMap> getInstructorLesson(Integer userId) {
		if(userRepository.existsById(userId)) {
			Integer instructorId = getInstructorId(userId);
			if(instructorId!=null) {
				
				List<LessonMap> list = timeTableService.getLessonList(1, instructorId);
				List<LessonMap> lessonMapList = new ArrayList<LessonMap>();
				
	
				for (LessonMap lessonMap : list) {
					if(!lessonMap.getTimeSlotData().get(0).equals("-")) {				
						lessonMapList.add(lessonMap);
					}
				}
				
				return lessonMapList;
			}
		}
		return null;
	}
	
	//get Assign student for specific lesson
	public List<LessonAssingStudentMap> getAssignStudent(Integer userId,Integer lessonId) {
		if(userRepository.existsById(userId) && lessonRepository.existsById(lessonId)) {
			
			List<StudentLesson> studentLessonList = studentLessonRepository.findByLessonIdAndInstructorId(lessonId,getInstructorId(userId));
			List<LessonAssingStudentMap> studentList = new ArrayList<LessonAssingStudentMap>();
			
			for (StudentLesson studentLesson : studentLessonList) {
				LessonAssingStudentMap object = new LessonAssingStudentMap();
				object.setStudentLessonId(studentLesson.getStudentLessonId());
				object.setName(studentLesson.getStudentId().getUserId().getFirstName());
				object.setNic(studentLesson.getStudentId().getUserId().getNic());
				object.setComplete(studentLesson.getComplete());
				
				studentList.add(object);
			}
			
			return studentList;
		}
		return null;
	}
	
	public LocalDate getLessonDate(Integer userId,Integer lessonId) {
		if(lessonRepository.existsById(lessonId)) {
			return studentLessonRepository.getDateByLessonIdAndInstructorId(lessonId, getInstructorId(userId));
		}
		return null;
	}
	
	public Integer markStudentLesson(Integer studentLessonId,Integer mark) {
		if(studentLessonRepository.existsById(studentLessonId) && mark>=0 && mark<=1) {
			StudentLesson object = studentLessonRepository.findByStudentLessonId(studentLessonId);
			object.setComplete(mark);
			studentLessonRepository.save(object);
			return 1;
		}
		return -1;
	}
	
	public StudentPractricalChartDataMap getPractricalLessonChartStudentData(Integer studentLessonId) {
		if(studentLessonRepository.existsById(studentLessonId)) {
			StudentLesson studentLessonObject = studentLessonRepository.findByStudentLessonId(studentLessonId);
			
			//get student following package details
			Integer packageId = studentLessonObject.getLessonId().getPackageId().getPackageId();
			Integer studentId = studentLessonObject.getStudentId().getStudentId();
			Integer transmission = studentLessonObject.getLessonId().getTransmission();
			String packageName=studentLessonObject.getLessonId().getPackageId().getTitle();
			LocalDate trialExamDate = studentLessonObject.getStudentId().getTrialDate();
			Integer totalLesson=0;
			if(packageId!=null && studentId!=null && transmission!=null && trialExamDate!=null) {
				if(transmission == 1) {
					totalLesson = studentLessonObject.getLessonId().getPackageId().getManualLes();
				}else {
					totalLesson = studentLessonObject.getLessonId().getPackageId().getAutoLes();
				}
				
				Integer completeLesson = studentLessonRepository.getStudentLessonCountByStudentIdAndPackageId(studentId, packageId, 1);
				Integer notCompleteLesson = studentLessonRepository.getStudentLessonCountByStudentIdAndPackageId(studentId, packageId, 0);
				Integer bookLesson = studentLessonRepository.getStudentLessonBookFutureCountByStudentIdAndPackageId(studentId, packageId);
				Integer remainLesson = totalLesson - (completeLesson+notCompleteLesson+bookLesson);
				
				//calculate remain days for trial examination
				LocalDate currentDate = timeTableService.getLocalCurrentDate();
				Long remainDays = currentDate.until(trialExamDate,ChronoUnit.DAYS);
				
				StudentPractricalChartDataMap object = new StudentPractricalChartDataMap();
				object.setPackageName(packageName);
				object.setCompleteLesson(completeLesson);
				object.setNotCompleteLesson(notCompleteLesson);
				object.setBookLesson(bookLesson);
				object.setRemainLesson(remainLesson);
				object.setTrialExamDate(trialExamDate);
				object.setRemainDays(remainDays);
				
				return object;
			}
			
		}
		
		return null;
	}
	
	//Helping Functions
	public Integer getInstructorId(Integer userId) {
		Staff staff = staffRepository.findByUserId(userId);
		if(staff != null) {
			Instructor instructor = instructorRepository.findByStaffId(staff.getStaffId());
			if(instructor!=null) {
				return instructor.getInstructorId();
			}
		}
		return null;
	}
}
