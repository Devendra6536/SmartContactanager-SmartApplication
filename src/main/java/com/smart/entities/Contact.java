package com.smart.entities;

import org.aspectj.weaver.tools.Trace;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Contacts")
public class Contact {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private int cid;
	@NotBlank(message = "Name can't be blank")
	@Size(min = 3, max = 50, message = "Name must be in between 3 to 50 character")
	private String name;
	@NotBlank(message = "Name can't be blank")
	@Size(min = 3, max = 50, message = "Name must be in between 3 to 50 character")
	private String secondname;
	@NotBlank(message = "work field can't be blank")
	private String work;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	@Column(length = 10)
	@Size(min=10, max=10, message="Phone no must be 10 characters only")
	private String phone;
	private String image;
	@Column(length = 50000)
	private String description;



	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JsonIgnore
	private User user;
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
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

	/*
	 * @Override public String toString() { return "Contact [cid=" + cid + ", name="
	 * + name + ", secondname=" + secondname + ", work=" + work + ", email=" + email
	 * + ", phone=" + phone + ", image=" + image + ", description=" + description +
	 * ", user=" + user + "]"; }
	 */
	
	
}
