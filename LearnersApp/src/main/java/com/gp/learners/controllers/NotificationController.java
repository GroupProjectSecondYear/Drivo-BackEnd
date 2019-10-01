package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.mapObject.NotificationDataMap;
import com.gp.learners.service.NotificationService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/notification/{userId}/{role}/{notificationType}")
	public ResponseEntity<List<NotificationDataMap>> getNotification(@PathVariable("userId") Integer userId,@PathVariable("role") Integer role,@PathVariable("notificationType") Integer notificationType) {
		List<NotificationDataMap> notificationList = notificationService.getNotification(userId, role, notificationType);
		if(notificationList!=null) {
			return ResponseEntity.ok(notificationList);
		}
		return ResponseEntity.notFound().build();
	}
}
