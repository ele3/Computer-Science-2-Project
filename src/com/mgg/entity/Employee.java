package com.mgg.entity;

/**
 * This class models an Employee's information, including their person code, 
 * type, last name, first name, address, and emails. But also, gets the unique discount.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Employee extends Person{
	public Employee(String personCode, String lastName, String firstName, Address address) {
		super(personCode, lastName, firstName, address);
	}

	public String getType() {
		return "E";
	}

	public Double getDiscount() {
		return 0.15;
	}
}
