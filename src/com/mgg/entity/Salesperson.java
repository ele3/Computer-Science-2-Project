package com.mgg.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a salesperson's information, including the person along
 * with their list of sales.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Salesperson {
	private Person person;
	private List<Sale> saleList;
	
	public Salesperson(Person person) {
		this.person = person;
		this.saleList = new ArrayList<>();
	}
	
	public Person getPerson() {
		return this.person;
	}
	
	public List<Sale> getSaleList() {
		return this.saleList;
	}
	
	public void addSale(Sale sale) {
		this.saleList.add(sale);
	}
	
	/**
	 * Outputs a summary of each individual Salesperson's grand total and number of sales.
	 * @return String singleString
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int salespersonSales = this.getSaleList().size();
		Double grandTotal = 0.0;
		
		for (Sale s : this.getSaleList()) {
			grandTotal += s.calculateGrandTotal();
		}
		String salesPersonName = this.getPerson().getLastName() + ", " + this.getPerson().getFirstName();
		sb.append(String.format("%-26s %5d          $%10.2f\n", salesPersonName, salespersonSales, grandTotal));
		
		String singleString = sb.toString();
		return singleString;
	}
}
