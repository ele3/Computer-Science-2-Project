package com.mgg.entity;

import java.util.List;

/**
 * This class models a Sale, including the sale code, store, customer
 * sales person, and list of transactions. Additionally, this class
 * calculates the Sub Total, Tax Total, Discount Total, and Grand Total.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Sale {
	private String saleCode;
	private Store store;
	private Person customer;
	private Person salesperson;
	private List<Transaction> transactionList;
	
	public Sale(String saleCode, Store store, Person customer, Person salesperson, List<Transaction> transactionList) {
		this.saleCode = saleCode;
		this.store = store;
		this.customer = customer;
		this.salesperson = salesperson;
		this.transactionList = transactionList;
	}

	public String getSaleCode() {
		return this.saleCode;
	}

	public Store getStore() {
		return this.store;
	}

	public Person getCustomer() {
		return this.customer;
	}

	public Person getSalesperson() {
		return this.salesperson;
	}

	public List<Transaction> getTransactionList() {
		return this.transactionList;
	}
	
	public Double calculateSubTotal() {
		Double total = 0.0;
		for (Transaction t : this.transactionList) {
			total = total + t.getSubTotalCost();
		}
		return total;
	}
	
	public Double calculateTaxTotal() {
		Double total = 0.0;
		for (Transaction t : this.transactionList) {
			total = total + t.getTaxedCost();
		}
		return total;
	}
	
	public Double calculateDiscountTotal() {
		Double total = 0.0;
		total = this.customer.getDiscount() * (this.calculateSubTotal() + this.calculateTaxTotal());
		Double roundTotal = (double) Math.round(total * 100) / 100;
		return roundTotal;
	}
	
	public Double calculateGrandTotal() {
		Double total = (this.calculateSubTotal() + this.calculateTaxTotal()) - this.calculateDiscountTotal();
		return total;
	}
	
	/**
	 * Builds a formated sale header string and outputs it.
	 * @return String singleString
	 */
	public String saleHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Sale  #%s\n", this.getSaleCode()));
		sb.append(String.format("Store #%s\n", this.getStore().getStoreCode()));
		sb.append(String.format("Customer: \n"));
		String customerName = this.getCustomer().getLastName() + ", " + this.getCustomer().getFirstName();
		sb.append(String.format("%s (%s)\n", customerName, this.getCustomer().getEmails()));
		sb.append(String.format("     %s\n", this.getCustomer().getAddress().getStreet()));
		sb.append(String.format("     %s %s %s %s\n", 
				this.getCustomer().getAddress().getCity(), 
				this.getCustomer().getAddress().getState(),
				this.getCustomer().getAddress().getZipCode(),
				this.getCustomer().getAddress().getCountry()));
		sb.append(String.format("\n"));
		
		sb.append(String.format("Sales Person: \n"));
		String salespersonName = this.getSalesperson().getLastName() + ", " + this.getSalesperson().getFirstName();
		sb.append(String.format("%s (%s)\n", salespersonName, this.getSalesperson().getEmails()));
		sb.append(String.format("     %s\n", this.getSalesperson().getAddress().getStreet()));
		sb.append(String.format("     %s %s %s %s\n", 
				this.getSalesperson().getAddress().getCity(), 
				this.getSalesperson().getAddress().getState(),
				this.getSalesperson().getAddress().getZipCode(),
				this.getSalesperson().getAddress().getCountry()));
		sb.append(String.format("\n"));
		sb.append(String.format("Item                                                               Total\n"));
		sb.append(String.format("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=-\n"));
		
		String singleString = sb.toString();
		return singleString;
	}
	
	/**
	 * Builds a formatted bill output consisting of subtotal, tax,
	 * discount, and grand total.
	 * @return String singleString
	 */
	public String getBillFormat() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("                                                             -=-=-=-=-=-\n"));
		sb.append(String.format("                                                    Subtotal $%10.2f\n", this.calculateSubTotal()));
		sb.append(String.format("                                                         Tax $%10.2f\n", this.calculateTaxTotal()));
		if (this.getCustomer().getType().equals("E")) {
			sb.append(String.format("                                           Discount (15.00%%) $%10.2f\n", this.calculateDiscountTotal()));
		}
		else if (this.getCustomer().getType().equals("P")) {
			sb.append(String.format("                                           Discount (10.00%%) $%10.2f\n", this.calculateDiscountTotal()));
		}
		else if (this.getCustomer().getType().equals("G")) {
			sb.append(String.format("                                            Discount (5.00%%) $%10.2f\n", this.calculateDiscountTotal()));
		}
		sb.append(String.format("                                                 Grand Total $%10.2f", this.calculateGrandTotal()));
		
		String singleString = sb.toString();
		return singleString;
	}
}
