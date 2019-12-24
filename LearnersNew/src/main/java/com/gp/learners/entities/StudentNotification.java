package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student_notification")
public class StudentNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentNotificationId;

	/*
	 * 0 --> Not View Notification 
	 * 1 --> View Notification
	 */
	@NotNull
	private Integer view;

	@ManyToOne
	@JoinColumn(name = "notification_id", referencedColumnName = "notification_id")
	@NotNull
	private Notification notificationId;

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id")
	@NotNull
	private Student studentId;

	public StudentNotification() {

	}

	public Integer getStudentNotificationId() {
		return studentNotificationId;
	}

	public void setStudentNotificationId(Integer studentNotificationId) {
		this.studentNotificationId = studentNotificationId;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public Notification getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Notification notificationId) {
		this.notificationId = notificationId;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "StudentNotification [studentNotificationId=" + studentNotificationId + ", view=" + view
				+ ", notificationId=" + notificationId + ", studentId=" + studentId + "]";
	}

}
