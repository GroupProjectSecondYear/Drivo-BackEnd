package com.gp.learners.entities.mapObject;

public class ProfitDataMap {

	private Double income;
	private Double outcome;
	private Double profit;

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getOutcome() {
		return outcome;
	}

	public void setOutcome(Double outcome) {
		this.outcome = outcome;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "ProfitDataMap [income=" + income + ", outcome=" + outcome + ", profit=" + profit + "]";
	}

}
