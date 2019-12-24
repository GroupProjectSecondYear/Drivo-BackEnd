package com.gp.learners.entities;

import java.time.LocalDate;

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
@Table(name="insurance_payment")
public class InsurancePayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer insurancePaymentId;

	@NotNull
	private LocalDate date;

	@NotNull
	@Min(0)
	private Double amount;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@NotNull
	private Integer year;

	@ManyToOne
	@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId")
	private Vehicle vehicleId;

	public Integer getInsurancePaymentId() {
		return insurancePaymentId;
	}

	public void setInsurancePaymentId(Integer insurancePaymentId) {
		this.insurancePaymentId = insurancePaymentId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Vehicle getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Vehicle vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Override
	public String toString() {
		return "InsurancePayment [insurancePaymentId=" + insurancePaymentId + ", date=" + date + ", amount=" + amount
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", year=" + year + ", vehicleId=" + vehicleId
				+ "]";
	}

}
