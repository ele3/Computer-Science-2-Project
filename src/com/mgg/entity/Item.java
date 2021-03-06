package com.mgg.entity;

/**
 * This class models a general item's information, including the item's
 * code and name, and gets the type and tax depending on the type of item.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public abstract class Item {
	private String itemCode;
	private String name;
	
	public Item(String itemCode, String name) {
		this.itemCode = itemCode;
		this.name = name;
	}

	public String getItemCode() {
		return this.itemCode;
	}

	public String getName() {
		return this.name;
	}
	
	public abstract String getType();
	public abstract Double getTax();
}
