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
public class StudentPaper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_paper_id")
	private Integer studentPaperId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id")
	private Student studentId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "paper_id", referencedColumnName = "paper_id")
	private Paper paperId;

	@NotNull
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	private LocalDate date;

	@NotNull
	private Integer marks;
	
//	@NotNull
//	private Integer[] answers;

	// constructor
	public StudentPaper() {

	}

	public Integer getStudentPaperId() {
		return studentPaperId;
	}

	public void setStudentPaperId(Integer studentPaperId) {
		this.studentPaperId = studentPaperId;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Paper getPaperId() {
		return paperId;
	}

	public void setPaperId(Paper paperId) {
		this.paperId = paperId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

//	public Integer[] getAnswers() {
//		return answers;
//	}
//
//	public void setAnswers(Integer[] answers) {
//		this.answers = answers;
//	}

	public StudentPaper(Integer studentPaperId, @NotNull Student studentId, @NotNull Paper paperId,
			@NotNull LocalDate date, @NotNull Integer marks) {
		super();
		this.studentPaperId = studentPaperId;
		this.studentId = studentId;
		this.paperId = paperId;
		this.date = date;
		this.marks = marks;
		//this.answers = answers;
	}
	
	
}
