package com.mgg;

/**
 * This class models a service's information, including their code,
 * name, and hourly rate.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class Service {
	private String code;
	private String type;
	private String name;
	private Double hourlyRate;
	
	public Service(String code, String type, String name, Double hourlyRate) {
		this.code = code;
		this.type = type;
		this.name = name;
		this.hourlyRate = hourlyRate;
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
	
	public Double getHourlyRate() {
		return this.hourlyRate;
	}
}
