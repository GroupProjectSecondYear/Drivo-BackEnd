package com.gp.learners.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Package {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "package_id")
	private Integer packageId;

	private String title;
	private String description;
	private String url;
	private Float price;
	private Integer status;
	@Column(name = "manual_les")
	private Integer manualLes;
	@Column(name = "auto_les")
	private Integer autoLes;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public VehicleCategory getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(VehicleCategory vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	@Override
	public String toString() {
		return "Package [packageId=" + packageId + ", title=" + title + ", description=" + description + ", url=" + url
				+ ", price=" + price + ", status=" + status + ", manualLes=" + manualLes + ", autoLes=" + autoLes
				+ ", vehicleCategoryId=" + vehicleCategoryId + "]";
	}

	

}
