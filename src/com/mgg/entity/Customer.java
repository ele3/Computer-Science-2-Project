package com.mgg.entity;

import java.util.ArrayList;

/**
 * This class models a Regular Customer's information, including their person code, 
 * type, last name, first name, address, and emails. But also, gets the unique discount.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Customer extends Person {
	public Customer(String personCode, String lastName, String firstName, Address address, ArrayList<String> emails) {
		super(personCode, lastName, firstName, address, emails);
	}

	public String getType() {
		return "C";
	}

	public Double getDiscount() {
		return 0.0;
	}
}
