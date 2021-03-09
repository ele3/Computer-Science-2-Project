package com.mgg.entity;

/**
 * This class models a Subscription's information, including the subscription's
 * code, type, name, and annual fee.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Subscription extends Item{
	private Double annualFee;
	
	public Subscription(String itemCode, String name, Double annualFee) {
		super(itemCode, name);
		this.annualFee = annualFee;
	}

	public Double getAnnualFee() {
		return this.annualFee;
	}

	public String getType() {
		return "SB";
	}

	public Double getTax() {
		return 0.0;
	}

	public Double getPrice() {
		return this.annualFee;
	}
}
