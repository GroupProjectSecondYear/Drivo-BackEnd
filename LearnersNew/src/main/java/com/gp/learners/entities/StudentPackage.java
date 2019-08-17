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
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "student_package")
public class StudentPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_package_id")
	private Integer studentPackageId;
	
	
	private LocalDate joinDate;
	private Integer transmission;// manual or auto

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id")
	private Student studentId;

	@ManyToOne
	@JoinColumn(name = "package_id", referencedColumnName = "package_id")
	private Package packageId;
	
	public StudentPackage() {
		
	}

	public Integer getStudentPackageId() {
		return studentPackageId;
	}

	public void setStudentPackageId(Integer studentPackageId) {
		this.studentPackageId = studentPackageId;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public Integer getTransmission() {
		return transmission;
	}

	public void setTransmission(Integer transmission) {
		this.transmission = transmission;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Package getPackageId() {
		return packageId;
	}

	public void setPackageId(Package packageId) {
		this.packageId = packageId;
	}

	@Override
	public String toString() {
		return "StudentPackage [studentPackageId=" + studentPackageId + ", joinDate=" + joinDate + ", transmission="
				+ transmission + ", studentId=" + studentId + ", packageId=" + packageId + "]";
	}

}
