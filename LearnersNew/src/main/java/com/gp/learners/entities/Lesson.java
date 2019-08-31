package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "lesson")
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lessonId;

	@NotNull
	@Size(min = 0, max = 6, message = "Day value should be between 0 and 6")
	private Integer day;

	@NotNull
	@Size(min = 1, max = 2, message = "Transmission value should be between 1 and 2")
	private Integer transmission;

	@NotNull
	private Integer numStu;// number of students for a lesson

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
	private Path pathId;

	public Lesson() {

	}

	public Lesson(Integer lessonId,
			@NotNull @Size(min = 0, max = 6, message = "Day value should be between 0 and 6") Integer day,
			@NotNull @Size(min = 1, max = 2, message = "Transmission value should be between 1 and 2") Integer transmission,
			@NotNull Integer numStu, @NotNull Instructor instructorId, @NotNull Package packageId,
			@NotNull TimeSlot timeSlotId, Path pathId) {
		super();
		this.lessonId = lessonId;
		this.day = day;
		this.transmission = transmission;
		this.numStu = numStu;
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

	@Override
	public String toString() {
		return "Lesson [lessonId=" + lessonId + ", day=" + day + ", transmission=" + transmission + ", numStu=" + numStu
				+ ", instructorId=" + instructorId + ", packageId=" + packageId + ", timeSlotId=" + timeSlotId
				+ ", pathId=" + pathId + "]";
	}

}
