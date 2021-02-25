package com.mgg;

import com.mgg.writer.JSONConverter;
import com.mgg.writer.XMLConverter;

/**
 * This program loads the data from the CSV files, parses said data, and 
 * then serializes the data into output files of XML and JSON.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class DataConverter {
	
	public static void main(String[] args) {
		XMLConverter.xmlPersonOutput();
		JSONConverter.jsonPersonOutput();
		
		XMLConverter.xmlStoreOutput();
		JSONConverter.jsonStoreOutput();
		
		XMLConverter.xmlItemOutput();
		JSONConverter.jsonItemOutput();
	}
}
