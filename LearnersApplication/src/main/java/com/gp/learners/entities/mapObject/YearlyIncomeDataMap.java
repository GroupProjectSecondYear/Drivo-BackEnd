package com.gp.learners.entities.mapObject;

import java.util.List;

public class YearlyIncomeDataMap {

	private List<Integer> years;
	private List<String> packages;
	private List<List<Double>> payments;

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

	public List<List<Double>> getPayments() {
		return payments;
	}

	public void setPayments(List<List<Double>> payments) {
		this.payments = payments;
	}

	@Override
	public String toString() {
		return "YearlyIncomeDataMap [years=" + years + ", packages=" + packages + ", payments=" + payments + "]";
	}

}
