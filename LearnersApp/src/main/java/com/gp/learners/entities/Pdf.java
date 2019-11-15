package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

//Pdf Entity
@Entity
public class Pdf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pdf_id")
	private Integer pdfId;

	@NotBlank(message = "resourse is mandatory")
	private String resource;             //Is this a URL???If its a url should validate

	@NotBlank(message = "description is mandatory")
	private String description;

	private String title;

	@ManyToOne
	@JoinColumn(name = "admin_staff_id", referencedColumnName = "admin_staff_id")
	private AdminStaff adminStaffId;

	private LocalDate addedDate;

	@DateTimeFormat(pattern = "MM-dd-YYYY" )
	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	// constructor
	public Pdf() {
		System.out.print("in Pdf entity adding"+this.pdfId);

	}

	public Pdf(Integer pdfId, @NotBlank(message = "resourse is mandatory") String resource, AdminStaff adminStaffId) {
		super();
		this.pdfId = pdfId;
		this.resource = resource;
		this.adminStaffId = adminStaffId;
	}

	public Integer getPdfId() {
		return pdfId;
	}

	public void setPdfId(Integer pdfId) {
		this.pdfId = pdfId;
	}

	public String getResource() {
		return resource;
	}

	void setResource(String resource) {
		this.resource = resource;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public AdminStaff getAdminStaffId() {
		return adminStaffId;
	}

	public void setAdminStaffId(AdminStaff adminStaffId) {
		this.adminStaffId = adminStaffId;
	}
	@Override
	public String toString() {
		return "Pdf [pdfId=" + pdfId + ", resource=" + resource + ", description=" + description + ", title=" + title
				+ ", adminStaffId=" + adminStaffId + ", addedDate=" + addedDate + "]";
	}
}
