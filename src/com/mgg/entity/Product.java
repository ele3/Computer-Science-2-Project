package com.mgg.entity;

/**
 * This class models a general Product's information, including its
 * tax and later gets the type of the specific product.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public abstract class Product extends Item {
	
	public Product(String itemCode, String name) {
		super(itemCode, name);
	}
	
	public Double getTax() {
		return 0.0725;
	}
	
	public abstract String getType();
}
