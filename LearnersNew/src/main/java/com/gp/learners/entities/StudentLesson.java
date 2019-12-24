package com.gp.learners.entities;

import java.sql.Date;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class StudentLesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentLessonId;

	@NotNull
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	private LocalDate date;

	@NotNull
	private Integer complete;

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id")
	@NotNull
	private Student studentId;

	@ManyToOne
	@JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
	@NotNull
	private Lesson lessonId;

	public StudentLesson() {

	}

	public StudentLesson(Integer studentLessonId, @NotNull LocalDate date, @NotNull Integer complete,
			@NotNull Student studentId, @NotNull Lesson lessonId) {
		super();
		this.studentLessonId = studentLessonId;
		this.date = date;
		this.complete = complete;
		this.studentId = studentId;
		this.lessonId = lessonId;
	}

	public Integer getStudentLessonId() {
		return studentLessonId;
	}

	public void setStudentLessonId(Integer studentLessonId) {
		this.studentLessonId = studentLessonId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Lesson getLessonId() {
		return lessonId;
	}

	public void setLessonId(Lesson lessonId) {
		this.lessonId = lessonId;
	}

	@Override
	public String toString() {
		return "StudentLesson [studentLessonId=" + studentLessonId + ", date=" + date + ", complete=" + complete
				+ ", studentId=" + studentId + ", lessonId=" + lessonId + "]";
	}

}
