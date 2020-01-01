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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

//Paper Entity
@Entity
public class Paper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paper_id")
	private Integer paperId;
	
	@NotBlank(message = "Title is mandatory")
	private String title;
	

	@NotNull
	@ManyToOne
	@JoinColumn(name = "admin_staff_id", referencedColumnName = "admin_staff_id")
	private AdminStaff adminStaffId;

	@NotNull
	@DateTimeFormat(pattern = "MM-dd-YYYY" )
	private LocalDate addedDate;

	
	// constructor
	public Paper() {
		System.out.print("in Paper entity adding"+this.paperId);

	}


	public Paper(Integer paperId, @NotBlank(message = "Title is mandatory") String title,
			@NotNull AdminStaff adminStaffId, @NotNull LocalDate addedDate) {
		super();
		this.paperId = paperId;
		this.title = title;
		this.adminStaffId = adminStaffId;
		this.addedDate = addedDate;
	}


	@Override
	public String toString() {
		return "Paper [paperId=" + paperId + ", title=" + title + ", adminStaffId=" + adminStaffId + ", addedDate="
				+ addedDate + "]";
	}


	public Integer getPaperId() {
		return paperId;
	}


	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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

	

	
}
