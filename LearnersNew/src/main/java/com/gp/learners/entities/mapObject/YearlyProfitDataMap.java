package com.gp.learners.entities.mapObject;

import java.util.List;

public class YearlyProfitDataMap {

	private List<Integer> years;
	private List<Double> income;
	private List<Double> outcome;
	private List<Double> profit;

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<Double> getIncome() {
		return income;
	}

	public void setIncome(List<Double> income) {
		this.income = income;
	}

	public List<Double> getOutcome() {
		return outcome;
	}

	public void setOutcome(List<Double> outcome) {
		this.outcome = outcome;
	}

	public List<Double> getProfit() {
		return profit;
	}

	public void setProfit(List<Double> profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "YearlyProfitDataMap [years=" + years + ", income=" + income + ", outcome=" + outcome + ", profit="
				+ profit + "]";
	}

}
