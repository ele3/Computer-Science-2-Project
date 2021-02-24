package com.mgg;

/**
 * This class models a product's information, including their code,
 * type, name, and base price.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class Product {
	private String code;
	private String type;
	private String name;
	private Double basePrice;
	
	public Product(String code, String type, String name, Double basePrice) {
		this.code = code;
		this.type = type;
		this.name = name;
		this.basePrice = basePrice;
	}

	public String getCode() {
		return this.code;
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public Double getBasePrice() {
		return this.basePrice;
	}
}
