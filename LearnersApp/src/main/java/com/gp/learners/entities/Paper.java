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
	private Integer no_of_questions;
	
	@NotNull
	private Integer no_of_answers;

	@NotNull
	@DateTimeFormat(pattern = "MM-dd-YYYY" )
	private LocalDate addedDate;
	
	
	// constructor
	public Paper() {

	}

	public Paper(Integer paperId, @NotBlank(message = "Title is mandatory") String title,
			@NotNull AdminStaff adminStaffId, @NotNull LocalDate addedDate, @NotNull Integer noOfQuestions,
			@NotNull Integer noOfAnswers) {
		super();
		this.paperId = paperId;
		this.title = title;
		this.adminStaffId = adminStaffId;
		this.addedDate = addedDate;
		this.no_of_questions = noOfQuestions;
		this.no_of_answers = noOfAnswers;
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

	public Integer getNo_of_questions() {
		return no_of_questions;
	}

	public void setNo_of_questions(Integer no_of_questions) {
		this.no_of_questions = no_of_questions;
	}

	public Integer getNo_of_answers() {
		return no_of_answers;
	}

	public void setNo_of_answers(Integer no_of_answers) {
		this.no_of_answers = no_of_answers;
	}

	@Override
	public String toString() {
		return "Paper [paperId=" + paperId + ", title=" + title + ", adminStaffId=" + adminStaffId + ", addedDate="
				+ addedDate + ", no_of_questions=" + no_of_answers + ", no_of_answers=" + no_of_answers + "]";
	}
	
	


}
