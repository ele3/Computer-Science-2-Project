package com.mgg.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mgg.entity.Address;
import com.mgg.entity.Customer;
import com.mgg.entity.Employee;
import com.mgg.entity.GiftCard;
import com.mgg.entity.GoldCustomer;
import com.mgg.entity.Item;
import com.mgg.entity.NewProduct;
import com.mgg.entity.Person;
import com.mgg.entity.PlatinumCustomer;
import com.mgg.entity.Service;
import com.mgg.entity.Store;
import com.mgg.entity.Subscription;
import com.mgg.entity.UsedProduct;

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
				Person personPlaceHolder = null;
				Address addressPlaceHolder = new Address(tokens[4], tokens[5], tokens[6], tokens[8], tokens[7]);
				if (tokens.length == 9) {
					emails = null;
				}
				else {
					for (int i = 9; i < tokens.length; i++) {
						emails.add(tokens[i]);
					}
				}
				
				if (tokens[1].equals("P")) {
					personPlaceHolder = new PlatinumCustomer(tokens[0], tokens[2], tokens[3], addressPlaceHolder, emails);
				}
				else if (tokens[1].equals("G")) {
					personPlaceHolder = new GoldCustomer(tokens[0], tokens[2], tokens[3], addressPlaceHolder, emails);
				}
				else if (tokens[1].equals("C")) {
					personPlaceHolder = new Customer(tokens[0], tokens[2], tokens[3], addressPlaceHolder, emails);
				}
				else if (tokens[1].equals("E")) {
					personPlaceHolder = new Employee(tokens[0], tokens[2], tokens[3], addressPlaceHolder, emails);
				}
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
				Item itemPlaceHolder = null;
				Double price = 0.0;
				if (tokens.length == 4) {
					if (!tokens[3].isEmpty()) {
						price = Double.parseDouble(tokens[3]);
					}
					else {
						price = null;
					}
				}
				else {
					price = null;
				}
				
				if (tokens[1].equals("PN")) {
					itemPlaceHolder = new NewProduct(tokens[0], tokens[2], price);
				}
				else if (tokens[1].equals("PU")) {
					itemPlaceHolder = new UsedProduct(tokens[0], tokens[2], price);
				}
				else if (tokens[1].equals("PG")) {
					itemPlaceHolder = new GiftCard(tokens[0], tokens[2]);
				}
				else if (tokens[1].equals("SV")) {
					itemPlaceHolder = new Service(tokens[0], tokens[2], price);
				}
				else if (tokens[1].equals("SB")) {
					itemPlaceHolder = new Subscription(tokens[0], tokens[2], price);
				}
				itemData.add(itemPlaceHolder);
			}
		}
		return itemData;
	}

}
