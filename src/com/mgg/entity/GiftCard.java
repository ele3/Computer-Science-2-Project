package com.mgg.entity;

/**
 * This class models a Gift Card's information, including the gift card's
 * code, type, and name.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class GiftCard extends Product{
	public GiftCard(String itemCode, String name) {
		super(itemCode, name);
	}
	
	@Override
	public String getType() {
		return "PG";
	}
}
