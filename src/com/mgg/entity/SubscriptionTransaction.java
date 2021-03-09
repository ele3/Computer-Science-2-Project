package com.mgg.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This class contains the additional parameters beginDate and endDate 
 * needed for Subscription in regards to sale along with Transaction's attributes.
 * The class also returns Sub Total Cost and Taxed Cost.
 *
 * Date: 03/08/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class SubscriptionTransaction extends Transaction {
	private String beginDate;
	private String endDate;
	
	public SubscriptionTransaction(Item item, String beginDate, String endDate) {
		super(item);
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	
	public String getBeginDate() {
		return this.beginDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}
	
	public LocalDate convertBeginDate() {
		LocalDate date = LocalDate.parse(this.beginDate);
		return date;
	}
	
	public LocalDate convertEndDate() {
		LocalDate date = LocalDate.parse(this.endDate);
		return date;
	}
	
	public long countDays() {
		long days = (ChronoUnit.DAYS.between(convertBeginDate(), convertEndDate())) + 1;
		return days;
	}

	public Double getSubTotalCost() {
		Double result = (double)this.countDays() * (this.getItem().getPrice() / 365);
		Double roundResult = (double) Math.round(result * 100) / 100;
		return roundResult;
	}
	
	public String getFormattedTransaction() {
		StringBuilder sb = new StringBuilder();
		long days = this.countDays();
		String tag = String.format("(Subscription #%s %d days@$%.2f/yr)",
				this.getItem().getItemCode(), 
				days,
				this.getItem().getPrice());
		sb.append(String.format("   %-57s $%10.2f\n", tag, this.getSubTotalCost()));
		
		String singleString = sb.toString();
		return singleString;
	}
}
