package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "salary_information")
public class SalaryInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salary_information_id")
	private Integer salaryInformationId;

	@NotNull
	private LocalDate updateDate;
	
	@NotNull
	@Min(1)
	@Max(12)
	private Integer applyMonth;

	@NotNull(message = "Staff Type mandatory")
	private Integer staffType;

	@NotNull(message = "Staff FullDay Salary mandotory")
	@Min(0)
	private Double fullDaySalary;

	@NotNull(message = "Staff HalfDay Salary mandotory")
	@Min(0)
	private Double halfDaySalary;

	@NotNull(message = "Staff nopay mandotory")
	@Min(0)
	private Double nopay;

	@NotNull(message = "AdminId mandotory")
	@OneToOne
	@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
	private Admin adminId;

	public Integer getSalaryInformationId() {
		return salaryInformationId;
	}

	public void setSalaryInformationId(Integer salaryInformationId) {
		this.salaryInformationId = salaryInformationId;
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

	public Integer getStaffType() {
		return staffType;
	}

	public void setStaffType(Integer staffType) {
		this.staffType = staffType;
	}

	public Double getFullDaySalary() {
		return fullDaySalary;
	}

	public void setFullDaySalary(Double fullDaySalary) {
		this.fullDaySalary = fullDaySalary;
	}

	public Double getHalfDaySalary() {
		return halfDaySalary;
	}

	public void setHalfDaySalary(Double halfDaySalary) {
		this.halfDaySalary = halfDaySalary;
	}

	public Double getNopay() {
		return nopay;
	}

	public void setNopay(Double nopay) {
		this.nopay = nopay;
	}

	public Admin getAdminId() {
		return adminId;
	}

	public void setAdminId(Admin adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "SalaryInformation [salaryInformationId=" + salaryInformationId + ", updateDate=" + updateDate
				+ ", applyMonth=" + applyMonth + ", staffType=" + staffType + ", fullDaySalary=" + fullDaySalary
				+ ", halfDaySalary=" + halfDaySalary + ", nopay=" + nopay + ", adminId=" + adminId + "]";
	}

}
