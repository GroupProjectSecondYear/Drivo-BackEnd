package com.gp.learners.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer attendanceId;
	private LocalDate date;
	private LocalTime comeTime;
	private LocalTime leaveTime;

	@ManyToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	private Staff staffId;

	public Attendance() {

	}

	public Integer getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Integer attendanceId) {
		this.attendanceId = attendanceId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getComeTime() {
		return comeTime;
	}

	public void setComeTime(LocalTime comeTime) {
		this.comeTime = comeTime;
	}

	public LocalTime getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(LocalTime leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Staff getStaffId() {
		return staffId;
	}

	public void setStaffId(Staff staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "Attendance [attendanceId=" + attendanceId + ", date=" + date + ", comeTime=" + comeTime + ", leaveTime="
				+ leaveTime + ", staffId=" + staffId + "]";
	}

}
