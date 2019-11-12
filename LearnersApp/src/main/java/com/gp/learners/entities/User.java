package com.gp.learners.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@NotNull(message = "First Name is mandatory")
	private String firstName;

	@NotNull(message = "Last Name is mandatory")
	private String lastName;

	@NotNull(message = "Telephone number mandatory")
	@Size(min = 10, max = 10, message = "Telephone number should have 10 digits")
	private String tel;

	@NotNull(message = "NIC is mandatory")
	@Size(min = 10, max = 10, message = "NIC number should have 10 characters")
	private String nic;

	@NotNull(message = "Address is mandatory")
	private String address;

	@Email(message = "Email Should be valid")
	@NotNull(message = "Email is mandatory")
	private String email;

	@NotNull(message = "Password is mandatory")
	private String password;

	@Column(name = "reg_date")
	@DateTimeFormat(pattern = "MM-dd-YYYY")
	private Date regDate;

	@NotNull
	private Integer status;
	@NotNull
	private Integer role;
	@NotNull
	private Integer profileImage;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Integer profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", tel=" + tel
				+ ", nic=" + nic + ", address=" + address + ", email=" + email + ", password=" + password + ", regDate="
				+ regDate + ", status=" + status + ", role=" + role + ", profileImage=" + profileImage + "]";
	}

}
