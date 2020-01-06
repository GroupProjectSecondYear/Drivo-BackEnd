package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vehicleId;

	@NotNull
	@Min(0)
	@Max(1)
	private Integer status;

	@NotNull
	private String number;

	@NotNull
	private String brand;

	@NotNull
	private String model;

	@NotNull
	@Size(min = 1, max = 2, message = "Transmission value should be between 1 and 2")
	private Integer transmission;// 1-->Manual,2-->Auto

	@NotNull
	@Size(min = 1, max = 2, message = "Fuel Type value should be between 1 and 2")
	private Integer fuelType;// 1-->Diesel,2-->Petrol

	private String document_lic;// pdf url
	private Integer insurancePeriod;// months

	@OneToOne
	@JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id")
	private Instructor instructorId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "vehicle_category_id", referencedColumnName = "vehicle_category_id")
	private VehicleCategory vehicleCategoryId;

	public Vehicle() {

	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getTransmission() {
		return transmission;
	}

	public void setTransmission(Integer transmission) {
		this.transmission = transmission;
	}

	public Integer getFuelType() {
		return fuelType;
	}

	public void setFuelType(Integer fuelType) {
		this.fuelType = fuelType;
	}

	public String getDocument_lic() {
		return document_lic;
	}

	public void setDocument_lic(String document_lic) {
		this.document_lic = document_lic;
	}

	public Integer getInsurancePeriod() {
		return insurancePeriod;
	}

	public void setInsurancePeriod(Integer insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}

	public Instructor getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Instructor instructorId) {
		this.instructorId = instructorId;
	}

	public VehicleCategory getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(VehicleCategory vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", status=" + status + ", number=" + number + ", brand=" + brand
				+ ", model=" + model + ", transmission=" + transmission + ", fuelType=" + fuelType + ", document_lic="
				+ document_lic + ", insurancePeriod=" + insurancePeriod + ", instructorId=" + instructorId
				+ ", vehicleCategoryId=" + vehicleCategoryId + "]";
	}

}
