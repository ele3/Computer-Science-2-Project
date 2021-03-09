package com.mgg.entity;

/**
 * This class contains the additional parameters employee and numberOfHours 
 * needed for Service in regards to sale along with Transaction's attributes.
 * The class also returns Sub Total Cost and Taxed Cost.
 *
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class ServiceTransaction extends Transaction {
	private Person employee;
	private Double numberOfHours;
	
	public ServiceTransaction(Item item, Person employee, Double numberOfHours) {
		super(item);
		this.employee = employee;
		this.numberOfHours = numberOfHours;
	}

	public Person getEmployee() {
		return this.employee;
	}
	
	public Double getNumberofHours() {
		return this.numberOfHours;
	}
	
	public Double getSubTotalCost() {
		Double result = (this.numberOfHours * this.getItem().getPrice());
		Double roundResult = (double) Math.round(result * 100) / 100;
		return roundResult;
	}
	
	public String getFormattedTransaction() {
		StringBuilder sb = new StringBuilder();
		String employeeName = this.getEmployee().getLastName() + 
				", " + this.getEmployee().getFirstName();
		Double hours = this.getNumberofHours();
		String tag = String.format("(Service #%s by %s %.2fhrs@$%.2f/hr)", 
				this.getItem().getItemCode(),
				employeeName,
				hours,
				this.getItem().getPrice());
		sb.append(String.format("   %-57s $%10.2f\n", tag, this.getSubTotalCost()));
		
		String singleString = sb.toString();
		return singleString;
	}
}
