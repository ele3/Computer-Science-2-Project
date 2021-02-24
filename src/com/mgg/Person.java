package com.mgg;

import java.util.ArrayList;

/**
 * This class models a person's information, including their person code, 
 * type of customer or employee, last name, first name, address, and emails.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class Person {
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
	private ArrayList<String> emails;
	
	public Person(String personCode, String lastName, String firstName, Address address, ArrayList<String> emails) {
		this.personCode = personCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.emails = emails;
	}
	
	public String getPersonCode() {
		return this.personCode;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public Address getAddress() {
		return this.address;
	}

	public ArrayList<String> getEmails() {
		return this.emails;
	}
	
}
