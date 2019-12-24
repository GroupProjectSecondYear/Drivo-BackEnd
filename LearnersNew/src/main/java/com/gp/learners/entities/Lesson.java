package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "lesson")
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lesson_id")
	private Integer lessonId;

	@NotNull
	@Range(min = 0, max = 6, message = "Day value should be between 0 and 6")
	private Integer day;

	@NotNull
	@Range(min = 1, max = 2, message = "Transmission value should be between 1 and 2")
	private Integer transmission;

	@NotNull
	@Range(min = 1, message = "Number of student should be greater than 0")
	private Integer numStu;// number of students for a lesson

	@NotNull
	private Integer status;

	@NotNull
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id")
	@NotNull
	private Instructor instructorId;

	@ManyToOne
	@JoinColumn(name = "package_id", referencedColumnName = "package_id")
	@NotNull
	private Package packageId;

	@ManyToOne
	@JoinColumn(name = "time_slot_id", referencedColumnName = "time_slot_id")
	@NotNull
	private TimeSlot timeSlotId;

	@ManyToOne
	@JoinColumn(name = "path_id", referencedColumnName = "path_id")
	@NotNull
	private Path pathId;

	public Lesson() {

	}

	public Lesson(Integer lessonId,
			@NotNull @Range(min = 0, max = 6, message = "Day value should be between 0 and 6") Integer day,
			@NotNull @Range(min = 1, max = 2, message = "Transmission value should be between 1 and 2") Integer transmission,
			@NotNull @Range(min = 1, message = "Number of student should be greater than 0") Integer numStu,
			@NotNull Integer status, @NotNull LocalDate date, @NotNull Instructor instructorId,
			@NotNull Package packageId, @NotNull TimeSlot timeSlotId, @NotNull Path pathId) {
		super();
		this.lessonId = lessonId;
		this.day = day;
		this.transmission = transmission;
		this.numStu = numStu;
		this.status = status;
		this.date = date;
		this.instructorId = instructorId;
		this.packageId = packageId;
		this.timeSlotId = timeSlotId;
		this.pathId = pathId;
	}

	public Integer getLessonId() {
		return lessonId;
	}

	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getTransmission() {
		return transmission;
	}

	public void setTransmission(Integer transmission) {
		this.transmission = transmission;
	}

	public Integer getNumStu() {
		return numStu;
	}

	public void setNumStu(Integer numStu) {
		this.numStu = numStu;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Instructor getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Instructor instructorId) {
		this.instructorId = instructorId;
	}

	public Package getPackageId() {
		return packageId;
	}

	public void setPackageId(Package packageId) {
		this.packageId = packageId;
	}

	public TimeSlot getTimeSlotId() {
		return timeSlotId;
	}

	public void setTimeSlotId(TimeSlot timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

	public Path getPathId() {
		return pathId;
	}

	public void setPathId(Path pathId) {
		this.pathId = pathId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Lesson [lessonId=" + lessonId + ", day=" + day + ", transmission=" + transmission + ", numStu=" + numStu
				+ ", status=" + status + ", date=" + date + ", instructorId=" + instructorId + ", packageId="
				+ packageId + ", timeSlotId=" + timeSlotId + ", pathId=" + pathId + "]";
	}

}
