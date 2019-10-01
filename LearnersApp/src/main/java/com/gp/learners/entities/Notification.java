package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notification_id")
	private Integer notificationId;
	
	/*
	 * 1 -->Update Lesson Notification
	 * 2 -->New Package add Notification
	 */
	@NotNull
	private Integer notificationType;
	
	@NotNull
	private String message;
	
	@NotNull
	private LocalDate date;
	
	public Notification() {
		
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(Integer notificationType) {
		this.notificationType = notificationType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", notificationType=" + notificationType
				+ ", message=" + message + ", date=" + date + "]";
	}
	
	
}
