package com.mgg.entity;

/**
 * This superclass for Transaction versions of Products, Service, and Subscription 
 * models a general Transaction's information, including an item's attributes, but also
 * gets the Sub Total Cost, the Taxed Cost, and formatted transaction string.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public abstract class Transaction {
	private Item item;
	
	public Transaction(Item item) {
		this.item = item;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public abstract Double getSubTotalCost();
	
	public Double getTaxedCost() {
		Double result = this.getSubTotalCost() * this.getItem().getTax();
		Double roundResult = (double) Math.round(result * 100) / 100;
		return roundResult;
	}
	
	public Double getTotalCost() {
		return (this.getSubTotalCost() + this.getTaxedCost());
	}
	
	public abstract String getFormattedTransaction();
}
