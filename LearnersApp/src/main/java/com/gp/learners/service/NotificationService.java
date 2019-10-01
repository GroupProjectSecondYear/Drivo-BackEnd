package com.gp.learners.service;


import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.InstructorNotification;
import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Notification;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.entities.StudentNotification;
import com.gp.learners.entities.mapObject.NotificationDataMap;
import com.gp.learners.repositories.InstructorNotificationRepository;
import com.gp.learners.repositories.LessonRepository;
import com.gp.learners.repositories.NotificationRepository;
import com.gp.learners.repositories.StudentLessonRepository;
import com.gp.learners.repositories.StudentNotificationRepository;
import com.gp.learners.repositories.UserRepository;



@Service
public class NotificationService {
		
	@Autowired 
	TimeTableService timeTableService;
	
	@Autowired
	InstructorService instructorService;
	
	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	StudentLessonRepository studentLessonRepository;
	
	@Autowired
	StudentNotificationRepository studentNotificationRepository;
	
	@Autowired
	InstructorNotificationRepository instructorNotificationRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	
	
	//getNotificationList
	public List<NotificationDataMap> getNotification(Integer userId,Integer role,Integer notificationType){
		
		
		if(userRepository.existsById(userId) && notificationType>-1 && notificationType<2) {
			List<NotificationDataMap> notificationList = new ArrayList<NotificationDataMap>();
			if(role==4) {//instructor
				List<InstructorNotification> instructorNotificationList = new ArrayList<InstructorNotification>();
				if(notificationType==1) {//read notification
					instructorNotificationList = instructorNotificationRepository.findNotificationByInstructorId(instructorService.getInstructorId(userId), 1);
				}else {//unread notification
					instructorNotificationList = instructorNotificationRepository.findNotificationByInstructorId(instructorService.getInstructorId(userId), 0);
				}
				
				for (InstructorNotification instructorNotification : instructorNotificationList) {
					NotificationDataMap object = new NotificationDataMap();
					object.setId(instructorNotification.getInstructorNotificationId());
					object.setMessage(instructorNotification.getMessage());
					object.setDateDiff(calculateTimeGap(instructorNotification.getDate()));
					
					String title="";
					if(instructorNotification.getNotificationType()==1) {
						title="Update Lesson";
					}
					else if(instructorNotification.getNotificationType()==2) {
						title="Assign New Instructor";
					}else {
						title="Assign New Lesson";
					}
					object.setTitle(title);
					
					notificationList.add(object);
				}
				
			}
			
			return notificationList;
		}
		
		return null;
	}
	
	public Boolean lessonUpdateNotification(Lesson updateLesson) {
		Integer lessonId = updateLesson.getLessonId();
		if(lessonId != null) {
			Lesson existLesson = lessonRepository.findByLessonId(updateLesson.getLessonId());
			
			/*
			 * Index :
			 * 	0 --> day (I/S)
			 * 	1 --> map (I/S)
			 *  2 --> timeSlot (I/S)
			 *  3 --> instructor (I/S)
			 *  4 --> NumStudent (I)
			 * 
			 */
			List<Boolean> lessonChangeArray = new ArrayList<Boolean>(5);
			checkLessonChanges(updateLesson,existLesson,lessonChangeArray);
			
			
			String msg = existLesson.getPackageId().getTitle()+" Lesson on "+timeTableService.getDay(existLesson.getDay())+
						 " at "+existLesson.getTimeSlotId().getStartTime()+":"+existLesson.getTimeSlotId().getFinishTime()+" Which ";
			String oldInstructorsMsg="";
			String newInstructorsMsg="";
			String instructorMsg="";
			String studentMsg;
			Boolean flag = false;
			
			if(lessonChangeArray.get(0)) {
				msg +="Day is chaged to "+timeTableService.getDay(updateLesson.getDay())+" ";
				flag=true;
			}
			
			if(lessonChangeArray.get(1)) {
				if(flag) {
					msg+="and ";
				}else {
					flag=true;
				}
				msg+="Lesson's path: "+existLesson.getPathId().getOrigin()+":"+existLesson.getPathId().getDestination()+" is changed to "+
					 updateLesson.getPathId().getOrigin()+":"+updateLesson.getPathId().getDestination()+" ";
			}
			
			if(lessonChangeArray.get(2)) {
				if(flag) {
					msg+="and ";
				}else {
					flag=true;
				}
				msg+="Time: "+existLesson.getTimeSlotId().getStartTime()+"-"+existLesson.getTimeSlotId().getFinishTime()+" is changed to "+
					 updateLesson.getTimeSlotId().getStartTime()+"-"+updateLesson.getTimeSlotId().getFinishTime()+" ";
			}
			
			instructorMsg=msg;
			studentMsg=msg;
			if(lessonChangeArray.get(3)) {
				if(flag) {
					studentMsg+="and ";
				}else {
					flag=true;
				}
				studentMsg+=" Assign new instructor "+updateLesson.getInstructorId().getStaffId().getName()+ " instead of "+existLesson.getInstructorId().getStaffId().getName()+" ";
				oldInstructorsMsg="New instructor assign for your lesson("+existLesson.getPackageId().getTitle()+") on "+timeTableService.getDay(existLesson.getDay())+" at "+existLesson.getTimeSlotId().getStartTime()+"-"+existLesson.getTimeSlotId().getFinishTime();
				newInstructorsMsg="Assign new " +updateLesson.getPackageId().getTitle()+" lesson for you on "+timeTableService.getDay(updateLesson.getDay())+" at "+updateLesson.getTimeSlotId().getStartTime()+"-"+updateLesson.getTimeSlotId().getFinishTime();
			}
			
			//NumberStudent Msg not implement
			
			//Save Student Notification in the notification table
			Notification studentNotification = new Notification();
			studentNotification.setNotificationType(1);
			studentNotification.setMessage(studentMsg);
			studentNotification.setDate(timeTableService.getLocalCurrentDate());
			studentNotification=notificationRepository.save(studentNotification);
			
			//save Student Notification in studentNotification table
			List<StudentLesson> studentLessonList = studentLessonRepository.findNotificationStudentListByLessonId(existLesson);
			for (StudentLesson studentLesson : studentLessonList) {
				StudentNotification object = new StudentNotification();
				object.setStudentId(studentLesson.getStudentId());
				object.setNotificationId(studentNotification);
				object.setView(0);
				
				studentNotificationRepository.save(object);
			}
			
			
			//save Instructor notification in notification table
			if(lessonChangeArray.get(3)) {//send notification both instructors(new instructor & old instructor)
				
				//old instructorNotification
				InstructorNotification oldInstructorNotification = new InstructorNotification();
				oldInstructorNotification.setMessage(oldInstructorsMsg);
				oldInstructorNotification.setDate(timeTableService.getLocalCurrentDate());
				oldInstructorNotification.setView(0);
				oldInstructorNotification.setInstructorId(existLesson.getInstructorId());
				oldInstructorNotification.setNotificationType(2);
				instructorNotificationRepository.save(oldInstructorNotification);
				
				//new instructorNotification
				InstructorNotification newInstructorNotification = new InstructorNotification();
				newInstructorNotification.setMessage(newInstructorsMsg);
				newInstructorNotification.setDate(timeTableService.getLocalCurrentDate());
				newInstructorNotification.setView(0);
				newInstructorNotification.setInstructorId(updateLesson.getInstructorId());
				newInstructorNotification.setNotificationType(3);
				instructorNotificationRepository.save(newInstructorNotification);
			}else {
				InstructorNotification instructorNotification = new InstructorNotification();
				instructorNotification.setMessage(instructorMsg);
				instructorNotification.setDate(timeTableService.getLocalCurrentDate());
				instructorNotification.setView(0);
				instructorNotification.setInstructorId(existLesson.getInstructorId());
				instructorNotification.setNotificationType(1);
				instructorNotificationRepository.save(instructorNotification);
			}
			
			return true;
		}
		return false;	
	}
	
	private void checkLessonChanges(Lesson updateLesson,Lesson existLesson,List<Boolean> lessonChangeArray ) {
		if(updateLesson.getDay() != existLesson.getDay()) {
			lessonChangeArray.add(true);
		}else {
			lessonChangeArray.add(false);
		}
		
		if(updateLesson.getPathId() != existLesson.getPathId()) {
			lessonChangeArray.add(true);
		}else {
			lessonChangeArray.add(false);
		}
		
		if(updateLesson.getTimeSlotId() != existLesson.getTimeSlotId()) {
			lessonChangeArray.add(true);
		}else {
			lessonChangeArray.add(false);
		}
		
		if(updateLesson.getInstructorId() != existLesson.getInstructorId()) {
			lessonChangeArray.add(true);
		}else {
			lessonChangeArray.add(false);
		}
		
		if(updateLesson.getNumStu() != existLesson.getNumStu()) {
			lessonChangeArray.add(true);
		}else {
			lessonChangeArray.add(false);
		}
	}
	
	//Helping Function
	private String calculateTimeGap(LocalDate date) {
		
		String timeGap="";
		
		LocalDate currentDate = timeTableService.getLocalCurrentDate();
		Period diff = Period.between(date, currentDate);
		Integer dayDiff = diff.getDays();
		Integer monthDiff = diff.getMonths();
		
		
		if(dayDiff == 0 && monthDiff==0) {
			timeGap="today";
		}else if(dayDiff<7 && monthDiff==0){
			timeGap=dayDiff+" day ago";
		}else if(monthDiff < 1) {
			WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
			int weekNumberOld = date.get(weekFields.weekOfMonth());
			int weekNumberCurrent = currentDate.get(weekFields.weekOfMonth());
			
			timeGap=(weekNumberCurrent-weekNumberOld)+" week ago";
		}else {
			timeGap=monthDiff+" month ago";
		}
		
		return timeGap;
	}

}
