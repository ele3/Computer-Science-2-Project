package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

/**
 * This class serializes the data from the CSV files gained from the 
 * Parser class into XML output files.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brendan Huynh
 * 
 */

public class XMLConverter {
	/**
	 * Converts the Person ArrayList into XML form, and outputs as an XML file.
	 */
	public static void xmlPersonOutput() {
		XStream xstream = new XStream();
		xstream.alias("person", Person.class);
		ArrayList<Person> personData = Parser.parsePersonData();
		File f = new File("data/Persons.xml");
		
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println("<persons>");
			for (Person p : personData) {
				String xml = xstream.toXML(p);
				pw.println(xml);
			}
			pw.println("</persons>");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts the Store ArrayList into XML form, and outputs as an XML file.
	 */
	public static void xmlStoreOutput() {
		XStream xstream = new XStream();
		xstream.alias("Store", Store.class);
		ArrayList<Store> storeData = Parser.parseStoreData();
		File f = new File("data/Stores.xml");
		
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println("<stores>");
			for (Store st : storeData) {
				String xml = xstream.toXML(st);
				pw.println(xml);
			}
			pw.println("</stores>");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts the Item ArrayList into XML form, and outputs as an XML file.
	 */
	public static void xmlItemOutput() {
		XStream xstream = new XStream();
		xstream.alias("Item", Item.class);
		ArrayList<Item> itemData = Parser.parseItemData();
		File f = new File("data/Items.xml");
		
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println("<items>");
			for (Item it : itemData) {
				String xml = xstream.toXML(it);
				pw.println(xml);
			}
			pw.println("</items>");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
