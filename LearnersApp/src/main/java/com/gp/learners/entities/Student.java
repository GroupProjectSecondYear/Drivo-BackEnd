package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Integer studentId;

	// @FutureOrPresent(message = "Not Valid Exam Date")
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	LocalDate examDate;

	// @FutureOrPresent(message = "Not Valid Trail Date") // trial date > exam date
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	LocalDate trialDate;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User userId;

	public Student() {

	}

	public Student(Integer studentId, LocalDate examDate, LocalDate trialDate, @NotNull User userId) {
		super();
		this.studentId = studentId;
		this.examDate = examDate;
		this.trialDate = trialDate;
		this.userId = userId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	public LocalDate getTrialDate() {
		return trialDate;
	}

	public void setTrialDate(LocalDate trialDate) {
		this.trialDate = trialDate;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", examDate=" + examDate + ", trialDate=" + trialDate + ", userId="
				+ userId + "]";
	}

}
