package com.gp.learners.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="admin_staff")
public class AdminStaff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="admin_staff_id")
	private Integer adminStaffId;

	@NotNull
	private Integer type;
	private String qualification;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="staff_id",referencedColumnName="staff_id")
	private Staff staffId;

	public AdminStaff() {

	}

	public Integer getAdminStaffId() {
		return adminStaffId;
	}

	public void setAdminStaffId(Integer adminStaffId) {
		this.adminStaffId = adminStaffId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Staff getStaffId() {
		return staffId;
	}

	public void setStaffId(Staff staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "AdminStaff [adminStaffId=" + adminStaffId + ", type=" + type + ", qualification=" + qualification
				+ ", staffId=" + staffId + "]";
	}

	

}
