package com.mgg.entity;

/**
 * This class models a Used Product's information, including the used product's
 * code, type, name, and used price.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class UsedProduct extends Product {
	private Double basePrice;
	
	public UsedProduct(String itemCode, String name, Double basePrice) {
		super(itemCode, name);
		this.basePrice = basePrice;
	}

	public Double getBasePrice() {
		return this.basePrice;
	}

	public String getType() {
		return "PU";
	}
	
	public Double getUsedPrice() {
		Double result = (basePrice * 0.8);
		Double roundResult = (double) Math.round(result * 100) / 100;
		return roundResult;
	}

	public Double getPrice() {
		return getUsedPrice();
	}
}
