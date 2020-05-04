package com.gp.learners.entities;

import java.time.LocalDate;

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
@Table(name = "leave_setting")
public class LeaveSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer leaveSettingId;

	@NotNull
	private LocalDate updateDate;

	@NotNull
	@Min(1)
	@Max(12)
	private Integer applyMonth;

	@Min(0)
	@NotNull(message = "Number of leaves cannot be null")
	private Integer numLeave;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
	private Admin adminId;

	public Integer getLeaveSettingId() {
		return leaveSettingId;
	}

	public void setLeaveSettingId(Integer leaveSettingId) {
		this.leaveSettingId = leaveSettingId;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getApplyMonth() {
		return applyMonth;
	}

	public void setApplyMonth(Integer applyMonth) {
		this.applyMonth = applyMonth;
	}

	public Integer getNumLeave() {
		return numLeave;
	}

	public void setNumLeave(Integer numLeave) {
		this.numLeave = numLeave;
	}

	public Admin getAdminId() {
		return adminId;
	}

	public void setAdminId(Admin adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "LeaveSetting [leaveSettingId=" + leaveSettingId + ", updateDate=" + updateDate + ", applyMonth="
				+ applyMonth + ", numLeave=" + numLeave + ", adminId=" + adminId + "]";
	}

}
