package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.InstructorNotification;
import com.gp.learners.entities.mapObject.NotificationDataMap;

public interface InstructorNotificationRepository extends JpaRepository<InstructorNotification, Integer>{
	
	@Query(value="select * from instructor_notification i where i.instructor_id = :instructorId and i.view = :notificationState order by date DESC",nativeQuery=true)
	public List<InstructorNotification> findNotificationByInstructorId(@Param("instructorId") Integer instructorId,@Param("notificationState") Integer notificationState);

	@Query(value="select * from instructor_notification i where i.instructor_notification_id = :instructorNotificationId ",nativeQuery=true)
	public InstructorNotification findByNotificationId(@Param("instructorNotificationId") Integer instructorNotificationId);
}
