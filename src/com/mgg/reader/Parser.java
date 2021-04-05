package com.mgg.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.mgg.entity.Address;
import com.mgg.entity.Customer;
import com.mgg.entity.Employee;
import com.mgg.entity.GiftCard;
import com.mgg.entity.GiftCardTransaction;
import com.mgg.entity.GoldCustomer;
import com.mgg.entity.Item;
import com.mgg.entity.NewProduct;
import com.mgg.entity.NewProductTransaction;
import com.mgg.entity.Person;
import com.mgg.entity.PlatinumCustomer;
import com.mgg.entity.Sale;
import com.mgg.entity.Salesperson;
import com.mgg.entity.Service;
import com.mgg.entity.ServiceTransaction;
import com.mgg.entity.Store;
import com.mgg.entity.StoreSale;
import com.mgg.entity.Subscription;
import com.mgg.entity.SubscriptionTransaction;
import com.mgg.entity.Transaction;
import com.mgg.entity.UsedProduct;
import com.mgg.entity.UsedProductTransaction;

/**
 * This class loads the data from the CSV files and parses said data into
 * an ArrayList of Person, Store, and Item.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
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
				String[] tokens = buffer.split(",");
				Person personPlaceHolder = null;
				Address addressPlaceHolder = new Address(tokens[4], tokens[5], tokens[6], tokens[8], tokens[7]);
				if (tokens[1].equals("P")) {
					personPlaceHolder = new PlatinumCustomer(tokens[0], tokens[2], tokens[3], addressPlaceHolder);
				}
				else if (tokens[1].equals("G")) {
					personPlaceHolder = new GoldCustomer(tokens[0], tokens[2], tokens[3], addressPlaceHolder);
				}
				else if (tokens[1].equals("C")) {
					personPlaceHolder = new Customer(tokens[0], tokens[2], tokens[3], addressPlaceHolder);
				}
				else if (tokens[1].equals("E")) {
					personPlaceHolder = new Employee(tokens[0], tokens[2], tokens[3], addressPlaceHolder);
				}
				personData.add(personPlaceHolder);
				for(int i=9; i<tokens.length; i++) {
					String email = tokens[i];
					personPlaceHolder.addEmail(email);
				}
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
	
	/**
	 * Reads the data from the input file, parses them into a Sale, 
	 * and then returns a Sale ArrayList
	 * @return Sale ArrayList
	 */
	public static ArrayList<Sale> parseSaleData() {
		String fileName = "data/Sales.csv";
		ArrayList<Sale> saleData = new ArrayList<>();
		ArrayList<Store> storeData = parseStoreData();
		ArrayList<Person> personData = parsePersonData();
		ArrayList<Item> itemData = parseItemData();
		
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
				Sale salePlaceHolder = null;
				String saleCode = tokens[0];
				Store store = null;
				Person customer = null;
				Person salesperson = null;
				for (Store st : storeData) {
					if (tokens[1].equals(st.getStoreCode())) {
						store = st;
					}
				}
				for (Person p : personData) {
					if (tokens[2].equals(p.getPersonCode())) {
						customer = p;
					}
				}
				for (Person p : personData) {
					if (tokens[3].equals(p.getPersonCode())) {
						salesperson = p;
					}
				}
				salePlaceHolder = new Sale(saleCode, store, customer, salesperson);
				Transaction t = null;
				Person serviceEmployee = null;
				int size = tokens.length - 1;
				int count = 4;
				
				while (count < size) {
					for (Item it : itemData) {
						if (tokens[count].equals(it.getItemCode())) {
							if (it.getType().equals("PN")) {
								t = new NewProductTransaction(it, Integer.parseInt(tokens[count + 1]));
								count = count + 2;
							}
							else if (it.getType().equals("PU")) {
								t = new UsedProductTransaction(it, Integer.parseInt(tokens[count + 1]));
								count = count + 2;
							}
							else if (it.getType().equals("PG")) {
								t = new GiftCardTransaction(it, Double.parseDouble(tokens[count + 1]));
								count = count + 2;
							}
							else if (it.getType().equals("SV")) {
								for (Person p : personData) {
									if (tokens[count + 1].equals(p.getPersonCode())) {
										serviceEmployee = p;
									}
								}
								t = new ServiceTransaction(it, serviceEmployee, Double.parseDouble(tokens[count + 2]));
								count = count + 3;
							}
							else if (it.getType().equals("SB")) {
								String beginDate = tokens[count + 1];
								String endDate = tokens[count + 2];
								t = new SubscriptionTransaction(it, beginDate, endDate);
								count = count + 3;
							}
							salePlaceHolder.addTransaction(t);
							break;
						}
					}
				}
				saleData.add(salePlaceHolder);
			}
		}
		return saleData;
	}
	
	/**
	 * Gets the data required from the Sale data & Person data for a Salesperson
	 * and returns a sorted Salesperson ArrayList
	 * @return salespersonData ArrayList
	 */
	public static ArrayList<Salesperson> parseSalespersonData() {
		ArrayList<Salesperson> salespersonData = new ArrayList<>();
		ArrayList<Person> personData = parsePersonData();
		ArrayList<Sale> saleData = parseSaleData();
		Salesperson sp = null;
		
		for (Person p : personData) {
			if(p.getType().equals("E")) {
				sp = new Salesperson(p);
				for (Sale s : saleData) {
					if (p.getPersonCode().equals(s.getSalesperson().getPersonCode())) {
						sp.addSale(s);
					}
				}
				salespersonData.add(sp);
			}
		}
		
		Collections.sort(salespersonData, new Comparator<Salesperson>() {
			public int compare(Salesperson a, Salesperson b) {
				int flag = a.getPerson().getLastName().compareTo(b.getPerson().getLastName());
				if (flag != 0) {
					return flag;
				}
				return a.getPerson().getFirstName().compareTo(b.getPerson().getFirstName());
			}
		});
		
		return salespersonData;
	}
	
	/**
	 * Gets the data required from the Sale data & Store data for a StoreSale
	 * and returns a sorted StoreSale ArrayList
	 * @return StoreSale ArrayList
	 */
	public static ArrayList<StoreSale> parseStoreSaleData() {
		ArrayList<StoreSale> storeSaleData = new ArrayList<>();
		ArrayList<Store> storeData = parseStoreData();
		ArrayList<Sale> saleData = parseSaleData();
		StoreSale ss = null;
		
		for (Store st : storeData) {
			ss = new StoreSale(st);
			for (Sale s : saleData) {
				if (s.getStore().getStoreCode().equals(ss.getStore().getStoreCode())) {
					ss.addSale(s);
				}
			}
			storeSaleData.add(ss);
		}
		
		Collections.sort(storeSaleData, new Comparator<StoreSale>() {
			public int compare(StoreSale a, StoreSale b) {
				int flag = a.getStore().getManager().getLastName().compareTo(b.getStore().getManager().getLastName());
				if (flag != 0) {
					return flag;
				}
				return a.getStore().getManager().getFirstName().compareTo(b.getStore().getManager().getFirstName());
			}
		});
		
		return storeSaleData;
	}

}
