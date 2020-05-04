package com.gp.learners.entities.mapObject;

import java.util.List;

public class YearlyOutComeDataMap {

	private List<Integer> years;
	private List<Double> salary;
	private List<Double> fuel;
	private List<Double> maintainance;
	private List<Double> insurance;

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<Double> getSalary() {
		return salary;
	}

	public void setSalary(List<Double> salary) {
		this.salary = salary;
	}

	public List<Double> getFuel() {
		return fuel;
	}

	public void setFuel(List<Double> fuel) {
		this.fuel = fuel;
	}

	public List<Double> getMaintainance() {
		return maintainance;
	}

	public void setMaintainance(List<Double> maintainance) {
		this.maintainance = maintainance;
	}

	public List<Double> getInsurance() {
		return insurance;
	}

	public void setInsurance(List<Double> insurance) {
		this.insurance = insurance;
	}

	@Override
	public String toString() {
		return "YearlyOutComeDataMap [years=" + years + ", salary=" + salary + ", fuel=" + fuel + ", maintainance="
				+ maintainance + ", insurance=" + insurance + "]";
	}

}
