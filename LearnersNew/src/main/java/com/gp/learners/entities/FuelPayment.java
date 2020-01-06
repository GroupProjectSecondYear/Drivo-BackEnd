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
@Table(name = "fuel_payment")
public class FuelPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fuelPaymentId;

	@NotNull
	@Min(1)
	@Max(12)
	private Integer month;

	@NotNull
	private LocalDate date;

	@NotNull
	private Integer year;

	@NotNull
	@Min(0)
	private Integer amount;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "admin_staff_id", referencedColumnName = "admin_staff_id")
	private AdminStaff adminStaffId;

	public Integer getFuelPaymentId() {
		return fuelPaymentId;
	}

	public void setFuelPaymentId(Integer fuelPaymentId) {
		this.fuelPaymentId = fuelPaymentId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public AdminStaff getAdminStaffId() {
		return adminStaffId;
	}

	public void setAdminStaffId(AdminStaff adminStaffId) {
		this.adminStaffId = adminStaffId;
	}

	@Override
	public String toString() {
		return "FuelPayment [fuelPaymentId=" + fuelPaymentId + ", month=" + month + ", date=" + date + ", year=" + year
				+ ", amount=" + amount + ", adminStaffId=" + adminStaffId + "]";
	}

}
