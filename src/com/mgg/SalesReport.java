package com.mgg;

import java.util.ArrayList;

import com.mgg.entity.Sale;
import com.mgg.reader.Parser;
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
		ArrayList<Sale> saleData = Parser.parseSaleData();
		
		SaleGenerator.generateSalespersonSummary();
		SaleGenerator.generateStoreSummary();
		
		for (Sale s : saleData) {
			System.out.println(s);
		}
	}
}
