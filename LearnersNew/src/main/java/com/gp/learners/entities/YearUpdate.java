package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "year_update")
public class YearUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer yearId;

	@NotNull
	private Integer updateYear;
	
	@NotNull
	@Max(1)
	@Min(0)
	private Integer systemUpdateStatus;
	private String message;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
	private Admin adminId;

	public Integer getYearId() {
		return yearId;
	}

	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}

	public Integer getUpdateYear() {
		return updateYear;
	}

	public void setUpdateYear(Integer updateYear) {
		this.updateYear = updateYear;
	}

	public Integer getSystemUpdateStatus() {
		return systemUpdateStatus;
	}

	public void setSystemUpdateStatus(Integer systemUpdateStatus) {
		this.systemUpdateStatus = systemUpdateStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Admin getAdminId() {
		return adminId;
	}

	public void setAdminId(Admin adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "YearUpdate [yearId=" + yearId + ", updateYear=" + updateYear + ", systemUpdateStatus="
				+ systemUpdateStatus + ", message=" + message + ", adminId=" + adminId + "]";
	}

}
