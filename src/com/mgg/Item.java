package com.mgg;

/**
 * This class models an item's information, including the item's
 * code, type, name, and cost.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class Item {
	private String itemCode;
	private String name;
	private Double basePrice;
	
	public Item(String itemCode, String name, Double basePrice) {
		this.itemCode = itemCode;
		this.name = name;
		this.basePrice = basePrice;
	}

	public String getItemCode() {
		return this.itemCode;
	}

	public String getName() {
		return this.name;
	}
	
	public Double getBasePrice() {
		return this.basePrice;
	}
}
