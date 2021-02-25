package com.mgg.entity;

/**
 * This class models an address, including a street, city, state, zip code, and country.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */
public class Address {
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	
	public Address(String street, String city, String state, String country, String zipCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
}

