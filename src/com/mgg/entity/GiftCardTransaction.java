package com.mgg.entity;

/**
 * This class contains the additional parameter of quantity needed for 
 * Gift Card in regards to sale along with Transaction's attributes.
 * The class also returns Sub Total Cost and Taxed Cost.
 *
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class GiftCardTransaction extends Transaction {
	private Double setPrice;
	
	public GiftCardTransaction(Item item, Double setPrice) {
		super(item);
		this.setPrice = setPrice;
	}
	
	public Double setPrice() {
		return this.setPrice;
	}
	
	public Double getSubTotalCost() {
		return this.setPrice;
	}
	
	public String getFormattedTransaction() {
		StringBuilder sb = new StringBuilder();
		String tag = String.format("(Gift Card #%s)",
				this.getItem().getItemCode());
		sb.append(String.format("   %-57s $%10.2f\n", tag, this.getSubTotalCost()));
		
		String singleString = sb.toString();
		return singleString;
	}
}
