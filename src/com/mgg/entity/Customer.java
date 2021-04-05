package com.mgg.entity;

/**
 * This class models a Regular Customer's information, including their person code, 
 * type, last name, first name, address, and emails. But also, gets the unique discount.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le  & Brock Melvin
 * 
 */

public class Customer extends Person {
	public Customer(String personCode, String lastName, String firstName, Address address) {
		super(personCode, lastName, firstName, address);
	}

	public String getType() {
		return "C";
	}

	public Double getDiscount() {
		return 0.0;
	}
}
