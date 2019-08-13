package com.gp.learners.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_category")
public class VehicleCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_category_id")
	private Integer vehicleCategoryId;

	private String category;

	public Integer getVehicleCategoryId() {
		return vehicleCategoryId;
	}
	
	
	public VehicleCategory() {
		
	}

	public void setVehicleCategoryId(Integer vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "VehicleCategory [vehicleCategoryId=" + vehicleCategoryId + ", category=" + category + "]";
	}

}
