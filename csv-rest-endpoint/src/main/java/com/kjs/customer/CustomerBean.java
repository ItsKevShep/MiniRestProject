package com.kjs.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "CUSTOMERS")
public class CustomerBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String reference;

	@Column(nullable = false)
	private String name;

	@Column(name = "addressOne")
	private String addressOne;

	@Column(name = "addressTwo")
	private String addressTwo;

	@Column
	private String town;

	@Column
	private String county;

	@Column
	private String country;

	@Column
	private String postcode;

	public Long getId() {
		return this.id;
	}
	
	public CustomerBean setReference(String reference) {
		this.reference = reference;
		return this;
	}
	
	public String getReference() {
		return this.reference;
	}
	
	public CustomerBean setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CustomerBean setAddressOne(String addressOne) {
		this.addressOne = addressOne;
		return this;
	}
	
	public String getAddressOne() {
		return this.addressOne;
	}
	
	public CustomerBean setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
		return this;
	}
	
	public String getAddressTwo() {
		return this.addressTwo;
	}
	
	public CustomerBean setTown(String town) {
		this.town = town;
		return this;
	}
	
	public String getTown() {
		return this.town;
	}
	
	public CustomerBean setCounty(String county) {
		this.county = county;
		return this;
	}
	
	public String getCounty() {
		return this.county;
	}
	
	public CustomerBean setCountry(String country) {
		this.country = country;
		return this;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public CustomerBean setPostcode(String postcode) {
		this.postcode = postcode;
		return this;
	}
	
	public String getPostcode() {
		return this.postcode;
	}
}
