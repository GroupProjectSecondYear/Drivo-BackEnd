package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "video_id")
	private Integer videoId;

	@NotBlank(message = "URL is mandatory")
	String url;
	
	@NotBlank(message = "Description is mandatory")
	String description;

	@ManyToOne
	@JoinColumn(name = "admin_staff_id", referencedColumnName = "admin_staff_id")
	private AdminStaff adminStaffId;

	// @FutureOrPresent(message = "Not Valid Trail Date") // trial date > exam date
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	LocalDate addedDate;

	// constructor
	public Video() {

	}

	public Video(Integer videoId, @NotBlank(message = "URL is mandatory") String url,
			@NotBlank(message = "Description is mandatory") String description, AdminStaff adminStaffId,
			LocalDate addedDate) {
		super();
		this.videoId = videoId;
		this.url = url;
		this.description = description;
		this.adminStaffId = adminStaffId;
		this.addedDate = addedDate;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AdminStaff getAdminStaffId() {
		return adminStaffId;
	}

	public void setAdminStaffId(AdminStaff adminStaffId) {
		this.adminStaffId = adminStaffId;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", url=" + url + ", description=" + description + ", adminStaffId="
				+ adminStaffId + ", addedDate=" + addedDate + "]";
	}

	

}
