package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.mapObject.NotificationDataMap;
import com.gp.learners.entities.mapObject.WebSocketCommunicationDataMap;
import com.gp.learners.service.NotificationService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	
	/*
	 * Notification Type 
	 * 0 --> Unread
	 * 1 --> Read
	 */
	@GetMapping("/notification/{userId}/{role}/{notificationState}")
	public ResponseEntity<List<NotificationDataMap>> getNotification(@PathVariable("userId") Integer userId,@PathVariable("role") Integer role,@PathVariable("notificationState") Integer notificationState) {
		List<NotificationDataMap> notificationList = notificationService.getNotification(userId, role, notificationState);
		if(notificationList!=null) {
			return ResponseEntity.ok(notificationList);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/notification/{role}")
	public ResponseEntity<Void> updateNotification(@RequestBody List<NotificationDataMap> notificationList,@PathVariable("role") Integer role){
		String reply=notificationService.updateNotification(role, notificationList);
		if(reply!=null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//WebSocket URL
	@MessageMapping("/hello")
 	@SendTo("/topic/greetings")
    public WebSocketCommunicationDataMap greeting(WebSocketCommunicationDataMap message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return message;
    }
}
