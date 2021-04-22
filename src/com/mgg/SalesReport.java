package com.mgg;

import com.mgg.writer.SaleGenerator;

/**
 * This program generates a sales report which consists of a 
 * summary report for salesperson and store sales and a detailed 
 * report for each individual sale.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class SalesReport {
	public static void main(String[] args) {
		//The commented code below is what is used to produce the Sales Summary Report Pre-Assignment 7
//		ArrayList<Sale> saleData = DatabaseParser.parseSaleData();
//		
//		SaleGenerator.generateSalespersonSummary();
//		SaleGenerator.generateStoreSummary();
//		
//		for (Sale s : saleData) {
//			System.out.println(s);
//		}
		
		SaleGenerator.generateSalesByCustomer();
		SaleGenerator.generateSalesByTotal();
		SaleGenerator.generateSalesByStore();
	} 
}
