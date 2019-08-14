package com.gp.learners.entities.mapObject;


//this class is relevant for StudentController.
//when add student following packages
//Student_Package
public class StudentPackageMap {
	private Integer packageId;
	private Integer transmission;

	public StudentPackageMap(Integer packageId, Integer transmission) {
		super();
		this.packageId = packageId;
		this.transmission = transmission;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getTransmission() {
		return transmission;
	}

	public void setTransmission(Integer transmission) {
		this.transmission = transmission;
	}

	@Override
	public String toString() {
		return "StudentPackageMap [packageId=" + packageId + ", transmission=" + transmission + "]";
	}

}
