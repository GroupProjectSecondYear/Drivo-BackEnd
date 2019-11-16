package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	
	@Query("from Notification where notificationType = :notificationType")
	public List<Notification> findByNotificationType(@Param("notificationType")Integer notificationType);
}
