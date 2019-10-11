package com.gp.learners.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lesson_day_feedback")
public class LessonDayFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lesson_day_feedback_id;
	private Integer day1;
	private Integer day2;
	private Integer time1;
	private Integer time2;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_package_id", referencedColumnName = "student_package_id")
	private StudentPackage studentPackageId;

	public Integer getLesson_day_feedback_id() {
		return lesson_day_feedback_id;
	}

	public void setLesson_day_feedback_id(Integer lesson_day_feedback_id) {
		this.lesson_day_feedback_id = lesson_day_feedback_id;
	}

	public Integer getDay1() {
		return day1;
	}

	public void setDay1(Integer day1) {
		this.day1 = day1;
	}

	public Integer getDay2() {
		return day2;
	}

	public void setDay2(Integer day2) {
		this.day2 = day2;
	}

	public Integer getTime1() {
		return time1;
	}

	public void setTime1(Integer time1) {
		this.time1 = time1;
	}

	public Integer getTime2() {
		return time2;
	}

	public void setTime2(Integer time2) {
		this.time2 = time2;
	}

	public StudentPackage getStudentPackageId() {
		return studentPackageId;
	}

	public void setStudentPackageId(StudentPackage studentPackageId) {
		this.studentPackageId = studentPackageId;
	}

	@Override
	public String toString() {
		return "LessonDayFeedback [lesson_day_feedback_id=" + lesson_day_feedback_id + ", day1=" + day1 + ", day2="
				+ day2 + ", time1=" + time1 + ", time2=" + time2 + ", studentPackageId=" + studentPackageId + "]";
	}

}
