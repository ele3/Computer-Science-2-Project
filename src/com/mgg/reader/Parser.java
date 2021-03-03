package com.mgg.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mgg.entity.Address;
import com.mgg.entity.Item;
import com.mgg.entity.Person;
import com.mgg.entity.Store;

/**
 * This class loads the data from the CSV files and parses said data into
 * an ArrayList of Person, Store, and Item.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le
 * 
 */

public class Parser {
	
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
	 * Reads the data from the input file, parses them into a Store, 
	 * and then returns a Store ArrayList
	 * @return Store ArrayList
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
	 * Reads the data from the input file, parses them into an Item, 
	 * and then returns an Item ArrayList
	 * @return Item ArrayList
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
				Double basePrice = 0.0;
				if (tokens.length == 4) {
					if (!tokens[3].isEmpty()) {
						basePrice = Double.parseDouble(tokens[3]);
					}
					else {
						basePrice = null;
					}
				}
				else {
					basePrice = null;
				}
				Item itemPlaceHolder = new Item(tokens[0], tokens[2], basePrice);
				itemData.add(itemPlaceHolder);
			}
		}
		return itemData;
	}

}
