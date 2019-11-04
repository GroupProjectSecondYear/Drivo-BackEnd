package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StaffLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer staffLeaveId;
	private String reason;
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	private Staff staffId;

	public Integer getStaffLeaveId() {
		return staffLeaveId;
	}

	public void setStaffLeaveId(Integer staffLeaveId) {
		this.staffLeaveId = staffLeaveId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Staff getStaffId() {
		return staffId;
	}

	public void setStaffId(Staff staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "StaffLeave [staffLeaveId=" + staffLeaveId + ", reason=" + reason + ", date=" + date + ", staffId="
				+ staffId + "]";
	}

}
