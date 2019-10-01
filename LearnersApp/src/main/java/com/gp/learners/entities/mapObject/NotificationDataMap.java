package com.gp.learners.entities.mapObject;

//Controller-->Notification Controller
//service --> Notification Service
//Reason --> Use in Notification Service getNotification() for assign notificaton result

public class NotificationDataMap {

	private Integer id;
	private String title;
	private String message;
	private String dateDiff;

	public NotificationDataMap() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateDiff() {
		return dateDiff;
	}

	public void setDateDiff(String dateDiff) {
		this.dateDiff = dateDiff;
	}

	@Override
	public String toString() {
		return "NotificationDataMap [id=" + id + ", title=" + title + ", message=" + message + ", dateDiff=" + dateDiff
				+ "]";
	}

}
