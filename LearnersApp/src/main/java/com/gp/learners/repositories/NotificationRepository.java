package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{

}
