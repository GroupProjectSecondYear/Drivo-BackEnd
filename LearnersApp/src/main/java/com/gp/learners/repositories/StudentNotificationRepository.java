package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentNotification;

public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Integer>{
	
	@Query(value="select * from student_notification s,notification n where s.notification_id=n.notification_id and  s.student_id = :studentId and s.view = :notificationState order by n.date DESC ",nativeQuery=true)
	public List<StudentNotification> findNotificationByStudentId(@Param("studentId")Integer studentId,@Param("notificationState")Integer notificationState);
	
	@Query(value="select * from student_notification s where s.student_notification_id = :studentNotificationId",nativeQuery=true)
	public StudentNotification findByStudentNotificationId(@Param("studentNotificationId") Integer notificationId);
	
	public void deleteByStudentId(Student student);
}
