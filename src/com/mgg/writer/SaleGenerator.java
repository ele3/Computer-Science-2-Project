package com.mgg.writer;

import java.util.ArrayList;

import com.mgg.entity.Person;
import com.mgg.entity.Sale;
import com.mgg.entity.Store;
import com.mgg.entity.Transaction;
import com.mgg.reader.Parser;

/**
 * This class contains all the generator methods needed for
 * SalesReport to output a readable summary of salesperson and
 * store as well as a detailed report for each sale.
 * 
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class SaleGenerator {
	/**
	 * Outputs a summary of each individual Salesperson's grand total and number of sales.
	 * Additionally, the overall number of sales and grand total is outputted.
	 */
	public static void generateSalespersonSummary() {
		ArrayList<Sale> saleData = Parser.parseSaleData();
		ArrayList<Person> personData = Parser.parsePersonData();
		
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
		for (Person p : personData) {
			int salespersonSales = 0;
			Double grandTotal = 0.0;
			if(p.getType().equals("E")) {
				for (Sale sale : saleData) {
					if(p.getPersonCode().equals(sale.getSalesperson().getPersonCode())) {
						salespersonSales = salespersonSales + 1;
						grandTotal = grandTotal + sale.calculateGrandTotal();
					}
				}
				String salesPersonName = p.getLastName() + ", " + p.getFirstName();
				System.out.printf("%-26s %5d          $%10.2f\n", salesPersonName, salespersonSales, grandTotal);
			}
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
		ArrayList<Sale> saleData = Parser.parseSaleData();
		ArrayList<Store> storeData = Parser.parseStoreData();
		
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
		for (Store s : storeData) {
			int storeSales = 0;
			Double grandTotal = 0.0;
			for (Sale sale : saleData) {
				if(s.getStoreCode().equals(sale.getStore().getStoreCode())) {
					storeSales = storeSales + 1;
					grandTotal = grandTotal + sale.calculateGrandTotal();
				}
			}
			String managerName = s.getManager().getLastName() + ", " + s.getManager().getFirstName();
			System.out.printf("%-10s %-26s %5d          $%10.2f\n", s.getStoreCode(), managerName, storeSales, grandTotal);
		}
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("%43d          $%10.2f\n", saleSummaryCount, saleSummaryGrandTotal);
		System.out.println();
	}
	
	/**
	 * Outputs each individual Sale's information, including sale code, store code,
	 * customer, salesperson, items, and overall bill.
	 * 
	 */
	public static void generateEachSaleReport() {
		ArrayList<Sale> saleData = Parser.parseSaleData();
		
		for (Sale s : saleData) {
			System.out.printf(s.saleHeader());
			
			for (Transaction t : s.getTransactionList()) {
				System.out.printf("%s\n", t.getItem().getName());
				System.out.printf(t.getFormattedTransaction());
			}
			
			System.out.println(s.getBillFormat());
		}
	}
}
