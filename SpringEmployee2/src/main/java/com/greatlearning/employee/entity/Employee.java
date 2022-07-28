package com.greatlearning.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name="EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String firstName;
	private String lastName;
	private String work;
	private String email;
	private String phone;
	private String image;
	@Column(length = 500)
	private String description;
	
	public Employee() {
		super();
	}

	public Employee(int id, String firstName, String lastName, String work, String email, String phone, String image,
			String description) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", work=" + work
				+ ", email=" + email + ", phone=" + phone + ", description=" + description + "]";
	}

	
}
