package com.gp.learners.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="staff_id")
	private Integer staffId;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotBlank(message = "NIC is mandatory")
	private String nic;// National Id Card Number

	@NotBlank(message = "Telephone Number is mandatory")
	private String tel;// Telephone Number

	@NotBlank(message = "Address is mandatory")
	private String address;

	@OneToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	private User userId;

	public Staff() {

	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", name=" + name + ", nic=" + nic + ", tel=" + tel + ", address=" + address
				+ ", userId=" + userId + "]";
	}

	
	

}
