package com.gp.learners.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Instructor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "instructor_id")
	private Integer instructorId;

	@NotNull
	private Integer licence;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	private Staff staffId;

	public Instructor() {

	}

	public Instructor(Integer instructorId, @NotNull Integer licence, @NotNull Staff staffId) {
		super();
		this.instructorId = instructorId;
		this.licence = licence;
		this.staffId = staffId;
	}

	public Integer getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Integer instructorId) {
		this.instructorId = instructorId;
	}

	public Integer getLicence() {
		return licence;
	}

	public void setLicence(Integer licence) {
		this.licence = licence;
	}

	public Staff getStaffId() {
		return staffId;
	}

	public void setStaffId(Staff staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "Instructor [instructorId=" + instructorId + ", licence=" + licence + ", staffId=" + staffId + "]";
	}

}
