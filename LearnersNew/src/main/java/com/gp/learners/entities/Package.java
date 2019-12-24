package com.gp.learners.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;

@Entity
public class Package {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "package_id")
	private Integer packageId;

	@NotBlank(message = "Title is mandatory")
	private String title;

	@NotBlank(message = "Description is mandatory")
	private String description;

	private Integer packageImage;

	@Min(0)
	@NotNull(message = "Price is mandatory")
	private Float price;

	@Min(0)
	@Max(1)
	private Integer status;

	@Column(name = "manual_les")
	private Integer manualLes;
	@Column(name = "auto_les")
	private Integer autoLes;

	@Min(0)
	@Max(100)
	@NotNull(message = "Basic Payment is mandatory")
	@Column(name = "basic_payment")
	private Integer basicPayment;

	@ManyToOne
	@JoinColumn(name = "vehicle_category_id", referencedColumnName = "vehicle_category_id")
	private VehicleCategory vehicleCategoryId;

	public Package() {

	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPackageImage() {
		return packageImage;
	}

	public void setPackageImage(Integer packageImage) {
		this.packageImage = packageImage;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getManualLes() {
		return manualLes;
	}

	public void setManualLes(Integer manualLes) {
		this.manualLes = manualLes;
	}

	public Integer getAutoLes() {
		return autoLes;
	}

	public void setAutoLes(Integer autoLes) {
		this.autoLes = autoLes;
	}

	public Integer getBasicPayment() {
		return basicPayment;
	}

	public void setBasicPayment(Integer basicPayment) {
		this.basicPayment = basicPayment;
	}

	public VehicleCategory getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(VehicleCategory vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	@Override
	public String toString() {
		return "Package [packageId=" + packageId + ", title=" + title + ", description=" + description
				+ ", packageImage=" + packageImage + ", price=" + price + ", status=" + status + ", manualLes="
				+ manualLes + ", autoLes=" + autoLes + ", basicPayment=" + basicPayment + ", vehicleCategoryId="
				+ vehicleCategoryId + "]";
	}

}
