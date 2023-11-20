package com.smart.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "USER")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
//	Assistant commissioner in UP police
	
	@NotBlank(message = "Name can't be blank")
	@Size(min = 3, max = 30, message = "min 3 and max 15 character are allowed")
	private String username;
	
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	
	@Column(name = "Password")
	@Size(min = 3, message = "Password must be 8 character long")
	private String passwd;
	
	private String role;
	private boolean enabled;
	private String imageurl;
	@Column(length = 500)
	@Size(min = 5, message = "must be 5 chracter and more")
	private String about;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "user",orphanRemoval = true)
	private List<Contact> conatcts =  new ArrayList<>();	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<Contact> getConatcts() {
		return conatcts;
	}
	public void setConatcts(List<Contact> conatcts) {
		this.conatcts = conatcts;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", passwd=" + passwd + ", role="
				+ role + ", enabled=" + enabled + ", imageurl=" + imageurl + ", about=" + about + ", conatcts="
				+ conatcts + "]";
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	 
	
	
}
