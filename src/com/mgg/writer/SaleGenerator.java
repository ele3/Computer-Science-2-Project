package com.mgg.writer;

import java.util.ArrayList;

import com.mgg.entity.Sale;
import com.mgg.entity.Salesperson;
import com.mgg.entity.StoreSale;
import com.mgg.reader.DatabaseParser;

/**
 * This class contains all the generator methods needed for
 * SalesReport to output a readable summary of salesperson and
 * store as well as a detailed report for each sale.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class SaleGenerator {
	/**
	 * Outputs a summary of each individual Salesperson's grand total and number of sales.
	 * Additionally, the overall number of sales and grand total is outputted.
	 */
	public static void generateSalespersonSummary() {
		ArrayList<Sale> saleData = DatabaseParser.parseSaleData();
		ArrayList<Salesperson> salespersonData = DatabaseParser.parseSalespersonData();
		
		Double saleSummaryGrandTotal = 0.0;
		int saleSummaryCount = 0;
		
		for (Sale sale : saleData) {
			saleSummaryGrandTotal = saleSummaryGrandTotal + sale.calculateGrandTotal();
			saleSummaryCount = saleSummaryCount + 1;
		}
		
		System.out.println("+-----------------------------------------------------+");
		System.out.println("| Salesperson Summary Report                          |");
		System.out.println("+-----------------------------------------------------+");
		System.out.println("Salesperson                    # Sales    Grand Total");
		for (Salesperson sp : salespersonData) {
			System.out.print(sp);
		}
		System.out.println("+-----------------------------------------------------+");
		System.out.printf("%32d          $%10.2f\n", saleSummaryCount, saleSummaryGrandTotal);
		System.out.println();
	}
	
	/**
	 * Outputs a summary of each individual Store's grand total and number of sales.
	 * Additionally, the overall number of sales and grand total is outputted.
	 * 
	 */
	public static void generateStoreSummary() {
		ArrayList<Sale> saleData = DatabaseParser.parseSaleData();
		ArrayList<StoreSale> storeSaleData = DatabaseParser.parseStoreSaleData();
		
		Double saleSummaryGrandTotal = 0.0;
		int saleSummaryCount = 0;
		
		for (Sale sale : saleData) {
			saleSummaryGrandTotal = saleSummaryGrandTotal + sale.calculateGrandTotal();
			saleSummaryCount = saleSummaryCount + 1;
		}
		
		System.out.println("+----------------------------------------------------------------+");
		System.out.println("| Store Sales Summary Report                                     |");
		System.out.println("+----------------------------------------------------------------+");
		System.out.println("Store      Manager                        # Sales    Grand Total");
		for (StoreSale st : storeSaleData) {
			System.out.print(st);
		}
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("%43d          $%10.2f\n", saleSummaryCount, saleSummaryGrandTotal);
		System.out.println();
	}
}
