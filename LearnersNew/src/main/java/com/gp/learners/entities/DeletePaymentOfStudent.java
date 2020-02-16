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
@Table(name="delete_payment_of_student")
public class DeletePaymentOfStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deletePaymentId;

	@NotNull
	@Min(0)
	private Float amount;

	@NotNull
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "package_id", referencedColumnName = "package_id")
	private Package packageId;

	public Integer getDeletePaymentId() {
		return deletePaymentId;
	}

	public void setDeletePaymentId(Integer deletePaymentId) {
		this.deletePaymentId = deletePaymentId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Package getPackageId() {
		return packageId;
	}

	public void setPackageId(Package packageId) {
		this.packageId = packageId;
	}

	@Override
	public String toString() {
		return "DeletePaymentOfStudent [deletePaymentId=" + deletePaymentId + ", amount=" + amount + ", date=" + date
				+ ", packageId=" + packageId + "]";
	}

}
