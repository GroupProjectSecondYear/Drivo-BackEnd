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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

//Pdf Entity
@Entity
public class Pdf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pdf_id")
	private Integer pdfId;

	@NotBlank(message = "Title is mandatory")
	private String title;

	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "admin_staff_id", referencedColumnName = "admin_staff_id")
	private AdminStaff adminStaffId;

	@NotNull
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	private LocalDate addedDate;

	// constructor
	public Pdf() {

	}

	public Pdf(Integer pdfId, @NotBlank(message = "Title is mandatory") String title, String description,
			AdminStaff adminStaffId, LocalDate addedDate) {
		super();
		this.pdfId = pdfId;
		this.title = title;
		this.description = description;
		this.adminStaffId = adminStaffId;
		this.addedDate = addedDate;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public Integer getPdfId() {
		return pdfId;
	}

	public void setPdfId(Integer pdfId) {
		this.pdfId = pdfId;
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
		return "Pdf [pdfId=" + pdfId + ", title=" + title + ", description=" + description + ", adminStaffId="
				+ adminStaffId + ", addedDate=" + addedDate + "]";
	}

}
