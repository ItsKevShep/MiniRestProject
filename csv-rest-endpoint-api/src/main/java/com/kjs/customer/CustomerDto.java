package com.kjs.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDto {

	@JsonProperty
	private String reference;

	@JsonProperty
	private String name;

	@JsonProperty
	private String addressOne;

	@JsonProperty
	private String addressTwo;

	@JsonProperty
	private String town;

	@JsonProperty
	private String county;

	@JsonProperty
	private String country;

	@JsonProperty
	private String postcode;

	public CustomerDto setReference(String reference) {
		this.reference = reference;
		return this;
	}
	
	public String getReference() {
		return this.reference;
	}
	
	public CustomerDto setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CustomerDto setAddressOne(String addressOne) {
		this.addressOne = addressOne;
		return this;
	}
	
	public String getAddressOne() {
		return this.addressOne;
	}
	
	public CustomerDto setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
		return this;
	}
	
	public String getAddressTwo() {
		return this.addressTwo;
	}
	
	public CustomerDto setTown(String town) {
		this.town = town;
		return this;
	}
	
	public String getTown() {
		return this.town;
	}
	
	public CustomerDto setCounty(String county) {
		this.county = county;
		return this;
	}
	
	public String getCounty() {
		return this.county;
	}
	
	public CustomerDto setCountry(String country) {
		this.country = country;
		return this;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public CustomerDto setPostcode(String postcode) {
		this.postcode = postcode;
		return this;
	}
	
	public String getPostcode() {
		return this.postcode;
	}
}
