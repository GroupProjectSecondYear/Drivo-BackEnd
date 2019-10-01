package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gp.learners.entities.StudentNotification;

public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Integer>{
	
}
