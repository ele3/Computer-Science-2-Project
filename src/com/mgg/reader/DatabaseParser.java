package com.mgg.reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

/**
 * This class loads the data straight from the database and parses said data into
 * an ArrayList of Person, Store, and Item.
 * 
 * Date: 04/05/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class DatabaseParser {
	
	private static final Logger LOGGER = LogManager.getLogger(DatabaseParser.class);

	static {
		//Logger being configurated
		Configurator.initialize(new DefaultConfiguration());
	    Configurator.setRootLevel(Level.INFO);
	}
	
	/**
	 * Reads the data from the database, parses them into a Person, 
	 * and then returns a Person ArrayList
	 * @return Person ArrayList
	 */
	public static ArrayList<Person> parsePersonData() {
		
		ArrayList<Person> personData = new ArrayList<>();
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT p.code, p.type, p.firstName, p.lastName, a.street, a.city, s.name AS state, c.name AS country, a.zipcode "
					+ "FROM Person p "
					+ "JOIN Address a ON p.addressId = a.addressId "
					+ "JOIN Country c ON a.countryId = c.countryId "
					+ "JOIN State s ON s.stateId = s.stateId "
					+ "GROUP BY p.personId;";
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Person personPlaceHolder = null;
				Address addressPlaceHolder = null;
				String personCode = rs.getString("code");
				String type = rs.getString("type");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String country = rs.getString("country");
				String zipcode = rs.getString("zipcode");
				addressPlaceHolder = new Address(street, city, state, country, zipcode);
				
				if (type.equals("P")) {
					personPlaceHolder = new PlatinumCustomer(personCode, lastName, firstName, addressPlaceHolder);
				}
				else if (type.equals("G")) {
					personPlaceHolder = new GoldCustomer(personCode, lastName, firstName, addressPlaceHolder);
				}
				else if (type.equals("C")) {
					personPlaceHolder = new Customer(personCode, lastName, firstName, addressPlaceHolder);
				}
				else if (type.equals("E")) {
					personPlaceHolder = new Employee(personCode, lastName, firstName, addressPlaceHolder);
				}
				personData.add(personPlaceHolder);
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query2 = "SELECT e.name FROM Email e WHERE personId = " 
					+ "(SELECT personId FROM Person p WHERE code = ?);";
		
		for (Person p : personData) {
			String personCode = p.getPersonCode();
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, personCode);
				rs = ps.executeQuery();
				while (rs.next()) {
					String email = rs.getString("name");
					p.addEmail(email);
				}
				rs.close();
				ps.close();
			}
			catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			conn.close();
		}
		catch (Exception e) {
			//ignore the exception
		}
		
		return personData;
	}
	
	/**
	 * Reads the data from the database, parses them into a Store, 
	 * and then returns a Store ArrayList
	 * @return Store ArrayList
	 */
	public static ArrayList<Store> parseStoreData() {
		ArrayList<Person> personData = parsePersonData();
		ArrayList<Store> storeData = new ArrayList<>();
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT st.code as storeCode, p.code as personCode, a.street, a.city, s.name AS state, c.name AS country, a.zipcode "
				+ "FROM Store st "
				+ "JOIN Person p ON p.personId = st.personId "
				+ "JOIN Address a ON a.addressId = st.addressId "
				+ "JOIN Country c ON a.countryId = c.countryId "
				+ "JOIN State s ON s.stateId = s.stateId "
				+ "GROUP BY st.storeId";
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Address addressPlaceHolder = null;
				
				String storeCode = rs.getString("storeCode");
				String personCode = rs.getString("personCode");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String country = rs.getString("country");
				String zipcode = rs.getString("zipcode");
				addressPlaceHolder = new Address(street, city, state, country, zipcode);
				for (Person p : personData) {
					if (personCode.equals(p.getPersonCode())) {
						Person personPlaceHolder = p;
						Store storePlaceHolder = new Store(storeCode, personPlaceHolder, addressPlaceHolder);
						storeData.add(storePlaceHolder);
					}
				}
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return storeData;
	}
	
	/**
	 * Reads the data from the database, parses them into an Item, 
	 * and then returns an Item ArrayList
	 * @return Item ArrayList
	 */
	public static ArrayList<Item> parseItemData() {
		ArrayList<Item> itemData = new ArrayList<>();
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT i.type, i.name, i.code, i.basePrice, i.hourlyRate, i.annualFee "
					+ "FROM Item i;";
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Item itemPlaceHolder = null;
				String type = rs.getString("type");
				String name = rs.getString("name");
				String itemCode = rs.getString("code");
				Double basePrice = rs.getDouble("basePrice");
				Double hourlyRate = rs.getDouble("hourlyRate");
				Double annualFee = rs.getDouble("annualFee");
				
				if (type.equals("PN")) {
					itemPlaceHolder = new NewProduct(itemCode, name, basePrice);
				}
				else if (type.equals("PU")) {
					itemPlaceHolder = new UsedProduct(itemCode, name, basePrice);
				}
				else if (type.equals("PG")) {
					itemPlaceHolder = new GiftCard(itemCode, name);
				}
				else if (type.equals("SV")) {
					itemPlaceHolder = new Service(itemCode, name, hourlyRate);
				}
				else if (type.equals("SB")) {
					itemPlaceHolder = new Subscription(itemCode, name, annualFee);
				}
				itemData.add(itemPlaceHolder);
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return itemData;
	}
	
	/**
	 * Reads the data from the database, parses them into a Sale, 
	 * and then returns a Sale ArrayList
	 * @return Sale ArrayList
	 */
	public static ArrayList<Sale> parseSaleData() {
		ArrayList<Sale> saleData = new ArrayList<>();
		ArrayList<Store> storeData = parseStoreData();
		ArrayList<Person> personData = parsePersonData();
		ArrayList<Item> itemData = parseItemData();
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT s.code AS saleCode, st.code AS storeCode, pc.code AS customerCode, psp.code AS salespersonCode "
				+ "FROM Sale s "
				+ "JOIN Store st ON st.storeId = s.storeId "
				+ "JOIN Customer c ON c.customerId = s.customerId "
				+ "JOIN Salesperson sp ON sp.salespersonId = s.salespersonId "
				+ "JOIN Person pc ON pc.personId = c.personId "
				+ "JOIN Person psp on psp.personId = sp.personId;";
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Sale salePlaceHolder = null;
				Store store = null;
				Person customer = null;
				Person salesperson = null;
				String saleCode = rs.getString("saleCode");
				String storeCode = rs.getString("storeCode");
				String customerCode = rs.getString("customerCode");
				String salespersonCode = rs.getString("salespersonCode");
				for (Store st : storeData) {
					if (storeCode.equals(st.getStoreCode())) {
						store = st;
					}
				}
				for (Person p : personData) {
					if (customerCode.equals(p.getPersonCode())) {
						customer = p;
					}
				}
				for (Person p : personData) {
					if (salespersonCode.equals(p.getPersonCode())) {
						salesperson = p;
					}
				}
				salePlaceHolder = new Sale(saleCode, store, customer, salesperson);
				saleData.add(salePlaceHolder);
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query2 = "SELECT i.code AS itemCode, i.type, si.quantity, p.code AS employeeCode, si.numberOfHours, "
				+ "si.beginDate, si.endDate, si.giftcardPrice "
				+ "FROM Item i "
				+ "LEFT JOIN SaleItem si ON i.itemId = si.itemId "
				+ "LEFT JOIN Sale s ON si.saleId = s.saleId "
				+ "LEFT JOIN Person p ON si.personId = p.personId "
				+ "WHERE s.saleId = (SELECT saleId FROM Sale WHERE code = ?);";
		
		for (Sale s : saleData) {
			String saleCode = s.getSaleCode();
			
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, saleCode);
				rs = ps.executeQuery();
				while (rs.next()) {
					String itemCode = rs.getString("itemCode");
					for (Item it : itemData) {
						if (itemCode.equals(it.getItemCode())) {
							Transaction t = null;
							Person serviceEmployee = null;
							
							if (it.getType().equals("PN")) {
								t = new NewProductTransaction(it, rs.getInt("quantity"));
							}
							else if (it.getType().equals("PU")) {
								t = new UsedProductTransaction(it, rs.getInt("quantity"));
							}
							else if (it.getType().equals("PG")) {
								t = new GiftCardTransaction(it, rs.getDouble("giftcardPrice"));
							}
							else if (it.getType().equals("SV")) {
								String employeeCode = rs.getString("employeeCode");
								for (Person p : personData) {
									if (employeeCode.equals(p.getPersonCode())) {
										serviceEmployee = p;
									}
								}
								t = new ServiceTransaction(it, serviceEmployee, rs.getDouble("numberOfHours"));
							}
							else if (it.getType().equals("SB")) {
								String beginDate = rs.getString("beginDate");
								String endDate = rs.getString("endDate");
								t = new SubscriptionTransaction(it, beginDate, endDate);
							}
							s.addTransaction(t);
							break;
						}
					}
				}
				rs.close();
				ps.close();
			}
			catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			conn.close();
		}
		catch (Exception e) {
			//ignore the exception
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
