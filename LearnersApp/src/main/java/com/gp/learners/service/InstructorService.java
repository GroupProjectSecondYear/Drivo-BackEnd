package com.gp.learners.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.entities.mapObject.LessonAssingStudentMap;
import com.gp.learners.entities.mapObject.LessonMap;
import com.gp.learners.repositories.InstructorRepository;
import com.gp.learners.repositories.LessonRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.StudentLessonRepository;
import com.gp.learners.repositories.UserRepository;

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
				object.setName(studentLesson.getStudentId().getName());
				object.setNic(studentLesson.getStudentId().getNic());
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
	
	//Helping Functions
	private Integer getInstructorId(Integer userId) {
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
