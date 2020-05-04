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
@Table(name="maintainance")
public class Maintainance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maintainanceId;

	@NotNull
	private String type;

	@NotNull
	private String description;

	@NotNull
	@Min(0)
	private Double amount;

	@NotNull
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId")
	private Vehicle vehicleId;

	public Integer getMaintainanceId() {
		return maintainanceId;
	}

	public void setMaintainanceId(Integer maintainanceId) {
		this.maintainanceId = maintainanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Vehicle getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Vehicle vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Override
	public String toString() {
		return "Maintainance [maintainanceId=" + maintainanceId + ", type=" + type + ", description=" + description
				+ ", amount=" + amount + ", date=" + date + ", vehicleId=" + vehicleId + "]";
	}

}
