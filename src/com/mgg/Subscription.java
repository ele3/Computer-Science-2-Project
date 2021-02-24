package com.mgg;

/**
 * This class models a subscription's information, including their code,
 * name, and annual fee.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class Subscription {
	private String code;
	private String type;
	private String name;
	private double annualFee;
	
	public Subscription(String code, String type, String name, Double annualFee) {
		this.code = code;
		this.type = type;
		this.name = name;
		this.annualFee = annualFee;
	}

	public String getCode() {
		return this.code;
	}
	
	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public double getAnnualFee() {
		return this.annualFee;
	}
}
