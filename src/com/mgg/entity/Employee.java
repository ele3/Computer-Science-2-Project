package com.mgg.entity;

import java.util.ArrayList;

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
	public Employee(String personCode, String lastName, String firstName, Address address, ArrayList<String> emails) {
		super(personCode, lastName, firstName, address, emails);
	}
	
	@Override
	public String getType() {
		return "E";
	}
	
	@Override
	public Double getDiscount() {
		return 0.15;
	}
}
