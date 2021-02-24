package com.mgg;

/**
 * This class models a store's information, including the storeCode,
 * manager, and address.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class Store {
	private String storeCode;
	private Person manager;
	private Address address;
	
	public Store(String storeCode, Person manager, Address address) {
		this.storeCode = storeCode;
		this.manager = manager;
		this.address = address;
	}

	public String getStoreCode() {
		return this.storeCode;
	}

	public Person getManager() {
		return this.manager;
	}

	public Address getAddress() {
		return this.address;
	}
}
