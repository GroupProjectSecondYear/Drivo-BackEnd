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
public class Complain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complain_id")
	private Integer complainId;

	@NotBlank(message = "Title is mandatory")
	String title;

	@NotBlank(message = "Complain is mandatory")
	String complain;

	@NotBlank(message = "View is mandatory")
	int view;
	
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	LocalDate date;
	
	
	@NotBlank(message = "Reply is mandatory")
	String reply;


	@ManyToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	private Staff staffId;

	// @FutureOrPresent(message = "Not Valid Trail Date") // trial date > exam date

	// constructor
	public Complain() {

	}

	public Complain(Integer complainId, @NotBlank(message = "Title is mandatory") String title,
			@NotBlank(message = "Complain is mandatory") String complain,
			@NotBlank(message = "View is mandatory") Integer view, LocalDate date,
			@NotBlank(message = "Reply is mandatory") String reply
			, Staff staffId) {
		super();
		this.complainId = complainId;
		this.title = title;
		this.complain = complain;
		this.view = view;
		this.date = date;
		this.reply = reply;
		this.staffId = staffId;
		
	}

	public Integer getComplainId() {
		return complainId;
	}

	public void setComplainId(Integer complainId) {
		this.complainId = complainId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public String getComplain() {
		return complain;
	}

	public void setComplain(String complain) {
		this.complain = complain;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Staff getStaffId() {
		return staffId;
	}

	public void setStaffId(Staff staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "Complain [complainId=" + complainId + ", title=" + title + ", complain=" + complain + ", view=" + view
				+ ", date=" + date + ", reply=" + reply + ",  staffId=" + staffId + "]";
	}

}