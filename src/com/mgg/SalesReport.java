package com.mgg;

import com.mgg.writer.SaleGenerator;

/**
 * This program generates a sales report which consists of a 
 * summary report for salesperson and store sales and a detailed 
 * report for each individual sale.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class SalesReport {
	public static void main(String[] args) {
		SaleGenerator.generateSalespersonSummary();
		SaleGenerator.generateStoreSummary();
		SaleGenerator.generateEachSaleReport();
	}
}
