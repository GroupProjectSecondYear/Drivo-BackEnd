package com.gp.learners.entities.mapObject;

import java.time.LocalDate;

public class PaymentEmailBodyMap {

	private String title;
	private LocalDate date;
	private Float amount;

	public PaymentEmailBodyMap() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PaymentEmailBodyMap [title=" + title + ", date=" + date + ", amount=" + amount + "]";
	}

}
