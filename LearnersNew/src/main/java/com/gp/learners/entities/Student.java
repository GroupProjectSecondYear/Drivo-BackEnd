package com.gp.learners.entities;

import java.time.LocalDate;
import java.util.Date;

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
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Integer studentId;

	@NotBlank(message = "Name is mandatory")
	String name;

	@Size(min = 10, max = 10, message = "Telephone number should be 10 numners")
	@NotBlank(message = "Telephone number is mandatory")
	String tel;

	@Size(min = 10, max = 10, message = "NIC number should be 10 numners")
	@NotBlank(message = "NIC is mandatory")
	String nic;
	
	@FutureOrPresent(message = "Not Valid Exam Date")
	@DateTimeFormat(pattern="MM-dd-YYYY")
	LocalDate examDate;

	@FutureOrPresent(message = "Not Valid Trail Date") // trial date > exam date
	@DateTimeFormat(pattern="MM-dd-YYYY")
	LocalDate trialDate;

	@NotBlank(message = "Address is mandatory")
	String address;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	private User userId;
	
	

	// constructor
	public Student() {

	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", tel=" + tel + ", nic=" + nic + ", examDate="
				+ examDate + ", trialDate=" + trialDate + ", address=" + address + ", userId=" + userId + "]";
	}

	

}
