package com.mgg.entity;

/**
 * This class models a service's information, including the service's
 * code, type, name, and hourly rate.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Service extends Item {
	private Double hourlyRate;
	
	public Service(String itemCode, String name, Double hourlyRate) {
		super(itemCode, name);
		this.hourlyRate = hourlyRate;
	}
	
	public Double getHourlyRate() {
		return this.hourlyRate;
	}
	
	@Override
	public String getType() {
		return "SV";
	}
	
	@Override
	public Double getTax() {
		return 0.0285;
	}
}
