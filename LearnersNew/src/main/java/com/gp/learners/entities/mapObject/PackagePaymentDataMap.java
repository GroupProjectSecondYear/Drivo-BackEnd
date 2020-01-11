package com.gp.learners.entities.mapObject;

//Controller-->Report  Controller
//service --> Report Service
//Reason --> Use for monthly package payment generation
public class PackagePaymentDataMap {

	private String packageName;
	private Double payment;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "PackagePaymentDataMap [packageName=" + packageName + ", payment=" + payment + "]";
	}

}
