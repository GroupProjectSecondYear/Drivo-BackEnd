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
@Table(name = "salary")
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer salaryId;

	@NotNull
	@Min(1)
	@Max(12)
	private Integer month;

	@NotNull
	private Integer year;

	@NotNull
	@Min(0)
	private Double totalPayment;

	@NotNull
	@Min(0)
	private Double payed;

	@NotNull
	@Min(0)
	private Double nopay;

	@NotNull
	@Min(0) // not pay total payment
	@Max(1) // pay total payment
	private Integer complete;

	private LocalDate date;

	@NotNull
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	@ManyToOne
	private Staff staffId;

	public Integer getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Integer salaryId) {
		this.salaryId = salaryId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Double getPayed() {
		return payed;
	}

	public void setPayed(Double payed) {
		this.payed = payed;
	}

	public Double getNopay() {
		return nopay;
	}

	public void setNopay(Double nopay) {
		this.nopay = nopay;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
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
		return "Salary [salaryId=" + salaryId + ", month=" + month + ", year=" + year + ", totalPayment=" + totalPayment
				+ ", payed=" + payed + ", nopay=" + nopay + ", complete=" + complete + ", date=" + date + ", staffId="
				+ staffId + "]";
	}

}
