package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

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
	
	/**
	 * Reads the data from the input file, parses them into a Person, 
	 * and then returns a Person ArrayList
	 * @return Person ArrayList
	 */
	public static ArrayList<Person> parsePersonData() {
		String fileName = "data/Persons.csv";
		ArrayList<Person> personData = new ArrayList<>();
		
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} 
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		String buffer = "";
		s.nextLine();
		
		while (s.hasNextLine()) {
			buffer = s.nextLine();
			if (!buffer.isEmpty()) {
				ArrayList<String> emails = new ArrayList<>();
				String[] tokens = buffer.split(",");
				Address addressPlaceHolder = new Address(tokens[4], tokens[5], tokens[6], tokens[8], tokens[7]);
				if (tokens.length == 9) {
					emails = null;
				}
				else {
					for (int i = 9; i < tokens.length; i++) {
						emails.add(tokens[i]);
					}
				}
				Person personPlaceHolder = new Person(tokens[0], tokens[2], tokens[3], addressPlaceHolder, emails);
				personData.add(personPlaceHolder);
			}
		}
		return personData;
	}
	
	/**
	 * Converts the Person ArrayList into XML form, and outputs as an XML file.
	 */
	public static void xmlPersonOutput() {
		XStream xstream = new XStream();
		xstream.alias("person", Person.class);
		ArrayList<Person> personData = parsePersonData();
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
	 * Converts the Person ArrayList into JSON form, and outputs as a JSON file.
	 */
	public static void jsonPersonOutput() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<Person> personData = parsePersonData();
		File f = new File("data/Persons.json");
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println("{");
			pw.println("\"persons\"" + ": " + gson.toJson(personData) + "}");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the data from the input file, parses them into a Store, 
	 * and then returns a Store ArrayList
	 * @return
	 */
	public static ArrayList<Store> parseStoreData() {
		String fileName = "data/Stores.csv";
		ArrayList<Store> storeData = new ArrayList<>();
		ArrayList<Person> personData = parsePersonData();
		
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		String buffer = "";
		s.nextLine();
		
		while (s.hasNextLine()) {
			buffer = s.nextLine();
			if (!buffer.isEmpty()) {
				String[] tokens = buffer.split(",");
				Address addressPlaceHolder = new Address(tokens[2], tokens[3], tokens[4], tokens[6], tokens[5]);
				for (Person p : personData) {
					if (tokens[1].equals(p.getPersonCode())) {
						Person personPlaceHolder = p;
						Store storePlaceHolder = new Store(tokens[0], personPlaceHolder, addressPlaceHolder);
						storeData.add(storePlaceHolder);
					}
				}
			}
		}
		return storeData;
	}
	
	/**
	 * Converts the Store ArrayList into XML form, and outputs as an XML file.
	 */
	public static void xmlStoreOutput() {
		XStream xstream = new XStream();
		xstream.alias("Store", Store.class);
		ArrayList<Store> storeData = parseStoreData();
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
	 * Converts the Store ArrayList into JSON form, and outputs as a JSON file.
	 */
	public static void jsonStoreOutput() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<Store> storeData = parseStoreData();
		File f = new File("data/Stores.json");
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println("{");
			pw.println("\"stores\"" + ": " + gson.toJson(storeData) + "}");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the data from the input file, parses them into an Item, 
	 * and then returns an Item ArrayList
	 * @return
	 */
	public static ArrayList<Item> parseItemData() {
		String fileName = "data/Items.csv";
		ArrayList<Item> itemData = new ArrayList<>();
		
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} 
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		String buffer = "";
		s.nextLine();
		
		while (s.hasNextLine()) {
			buffer = s.nextLine();
			if (!buffer.isEmpty()) {
				String[] tokens = buffer.split(",", -1);
				if (!tokens[3].isEmpty()) {
					Item itemPlaceHolder = new Item(tokens[0], tokens[2], Double.parseDouble(tokens[3]));
					itemData.add(itemPlaceHolder);
				}
				else {
					Item itemPlaceHolder = new Item(tokens[0], tokens[2], null);
					itemData.add(itemPlaceHolder);
				}
			}
		}
		return itemData;
	}
	
	/**
	 * Converts the Item ArrayList into XML form, and outputs as an XML file.
	 */
	public static void xmlItemOutput() {
		XStream xstream = new XStream();
		xstream.alias("Item", Item.class);
		ArrayList<Item> itemData = parseItemData();
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
	
	/**
	 * Converts the Store ArrayList into JSON form, and outputs as a JSON file.
	 */
	public static void jsonItemOutput() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<Item> itemData = parseItemData();
		File f = new File("data/Items.json");
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println("{");
			pw.println("\"items\"" + ": " + gson.toJson(itemData) + "}");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		xmlPersonOutput();
		jsonPersonOutput();
		
		xmlStoreOutput();
		jsonStoreOutput();
		
		xmlItemOutput();
		jsonItemOutput();

	}

}
