package com.mgg.entity;

/**
 * This class contains the additional parameter of quantity needed for 
 * Used Product in regards to sale along with Transaction's attributes.
 * The class also returns Sub Total Cost and Taxed Cost.
 *
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class UsedProductTransaction extends Transaction {
	private int quantity;
	
	public UsedProductTransaction(Item item, int quantity) {
		super(item);
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return this.quantity;
	}

	public Double getSubTotalCost() {
		Double result = (this.quantity * this.getItem().getPrice());
		Double roundResult = (double) Math.round(result * 100) / 100;
		return roundResult;
	}
	
	public String getFormattedTransaction() {
		StringBuilder sb = new StringBuilder();
		String tag = String.format("(Used Item #%s @$%.2f/ea)",
				this.getItem().getItemCode(), 
				this.getItem().getPrice());
		sb.append(String.format("   %-57s $%10.2f\n", tag, this.getSubTotalCost()));
		
		String singleString = sb.toString();
		return singleString;
	}
}
