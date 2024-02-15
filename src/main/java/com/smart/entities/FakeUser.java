package com.smart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class FakeUser {
	
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String firstName;
	private String lastName;
	private String country;
	private String state;
	private String city;
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "FakeUser [firstName=" + firstName + ", lastName=" + lastName + ", country=" + country + ", state="
				+ state + ", city=" + city + "]";
	}
	public FakeUser(String firstName, String lastName, String country, String state, String city) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.state = state;
		this.city = city;
	}
	public FakeUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
