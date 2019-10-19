package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "work_time")
public class WorkTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer workTimeId;
	private Integer fullDay;
	private Integer halfDay;

	@ManyToOne
	@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
	private Admin adminId;

	public Integer getWorkTimeId() {
		return workTimeId;
	}

	public void setWorkTimeId(Integer workTimeId) {
		this.workTimeId = workTimeId;
	}

	public Integer getFullDay() {
		return fullDay;
	}

	public void setFullDay(Integer fullDay) {
		this.fullDay = fullDay;
	}

	public Integer getHalfDay() {
		return halfDay;
	}

	public void setHalfDay(Integer halfDay) {
		this.halfDay = halfDay;
	}

	public Admin getAdminId() {
		return adminId;
	}

	public void setAdminId(Admin adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "WorkTime [workTimeId=" + workTimeId + ", fullDay=" + fullDay + ", halfDay=" + halfDay + ", adminId="
				+ adminId + "]";
	}

}
