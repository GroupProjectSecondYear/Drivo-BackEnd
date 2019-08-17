package com.gp.learners.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "course_fee")
@Entity
public class CourseFee {

	@Id
	@Column(name = "course_fee_id")
	private Integer courseFeeId;

	private Date date;
	private Double amount;
	private Integer method;

	@ManyToOne
	@JoinColumn(name = "student_package_id", referencedColumnName = "student_package_id")
	private StudentPackage studentPackageId;

	public CourseFee() {

	}

	public CourseFee(Integer courseFeeId, Date date, Double amount, Integer method, StudentPackage studentPackageId) {
		super();
		this.courseFeeId = courseFeeId;
		this.date = date;
		this.amount = amount;
		this.method = method;
		this.studentPackageId = studentPackageId;
	}

	public Integer getCourseFeeId() {
		return courseFeeId;
	}

	public void setCourseFeeId(Integer courseFeeId) {
		this.courseFeeId = courseFeeId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public StudentPackage getStudentPackageId() {
		return studentPackageId;
	}

	public void setStudentPackageId(StudentPackage studentPackageId) {
		this.studentPackageId = studentPackageId;
	}

	@Override
	public String toString() {
		return "CourseFee [courseFeeId=" + courseFeeId + ", date=" + date + ", amount=" + amount + ", method=" + method
				+ ", studentPackageId=" + studentPackageId + "]";
	}

}
