package com.mgg.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a store's information, including the store along
 * with its list of sales.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class StoreSale {
	private Store store;
	private List<Sale> saleList;
	
	public StoreSale(Store store) {
		this.store = store;
		this.saleList = new ArrayList<>();
	}
	
	public Store getStore() {
		return this.store;
	}
	
	public List<Sale> getSaleList() {
		return this.saleList;
	}
	
	public void addSale(Sale sale) {
		this.saleList.add(sale);
	}
	
	/**
	 * Outputs a summary of each individual Store's manager, grand total and number of sales.
	 * @return String singleString
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int numStoreSales = this.getSaleList().size();
		Double grandTotal = 0.0;
		
		for (Sale s : this.getSaleList()) {
			grandTotal += s.calculateGrandTotal();
		}
		
		String managerName = this.getStore().getManager().getLastName() + ", " + this.getStore().getManager().getFirstName();
		sb.append(String.format("%-10s %-26s %5d          $%10.2f\n", this.getStore().getStoreCode(), managerName, numStoreSales, grandTotal));
		
		String singleString = sb.toString();
		return singleString;
	}
	
}
