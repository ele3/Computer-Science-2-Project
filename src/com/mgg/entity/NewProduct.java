package com.mgg.entity;

/**
 * This class models a New Product's information, including the new product's
 * code, type, name, and basePrice.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class NewProduct extends Product{
	private Double basePrice;
	
	public NewProduct(String itemCode, String name, Double basePrice) {
		super(itemCode, name);
		this.basePrice = basePrice;
	}
	
	public Double getBasePrice() {
		return this.basePrice;
	}

	public String getType() {
		return "PN";
	}

	public Double getPrice() {
		return this.basePrice;
	}
}
