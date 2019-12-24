package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "leave_setting")
public class LeaveSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer leaveSettingId;

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
		return "LeaveSetting [leaveSettingId=" + leaveSettingId + ", numLeave=" + numLeave + ", adminId=" + adminId
				+ "]";
	}

}
