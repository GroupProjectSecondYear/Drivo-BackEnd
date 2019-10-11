package com.gp.learners.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "instructor_notification")
public class InstructorNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer instructorNotificationId;
	private String message;
	private Integer view;

	/*
	 * 1 --> Lesson Update(without instructor change) 
	 * 2 --> Assign New Instructor(Instead of current Instructor) 
	 * 3 --> Add New Lesson for instructor
	 * 4 --> Lesson Deactivate
	 */
	private Integer notificationType;
	private LocalDate date;
	private LocalTime time;

	@ManyToOne
	@JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id")
	private Instructor instructorId;

	public InstructorNotification() {

	}

	public Integer getInstructorNotificationId() {
		return instructorNotificationId;
	}

	public void setInstructorNotificationId(Integer instructorNotificationId) {
		this.instructorNotificationId = instructorNotificationId;
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

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Instructor getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Instructor instructorId) {
		this.instructorId = instructorId;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "InstructorNotification [instructorNotificationId=" + instructorNotificationId + ", message=" + message
				+ ", view=" + view + ", notificationType=" + notificationType + ", date=" + date + ", instructorId="
				+ instructorId + "]";
	}

}
