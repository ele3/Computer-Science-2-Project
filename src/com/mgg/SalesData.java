package com.mgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.mgg.reader.DatabaseInfo;

/**
 * Database interface class
 * 
 * Date: 04/14/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 */
public class SalesData {

	/**
	 * Removes all sales records from the database.
	 */
	public static void removeAllSales() {
		emptyTable("SaleItem");
		emptyTable("Sale");
	}

	/**
	 * Removes the single sales record associated with the given
	 * <code>saleCode</code>
	 * 
	 * @param saleCode
	 */
	public static void removeSale(String saleCode) {
		if (saleCode == null || saleCode.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		
		int saleId = getId("Sale", saleCode);
		
		String query = "DELETE FROM SaleItem where saleId = ?";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.executeUpdate();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query2 = "DELETE FROM Sale where saleId = ?";
		
		try {
			ps = conn.prepareStatement(query2);
			ps.setInt(1, saleId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Clears all tables of the database of all records.
	 */
	public static void clearDatabase() {
		emptyTable("SaleItem");
		emptyTable("Item");
		emptyTable("Sale");
		emptyTable("Store");
		emptyTable("Email");
		emptyTable("Person");
		emptyTable("Address");
		emptyTable("Country");
		emptyTable("State");
	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>type</code> will be one of "E", "G", "P" or "C" depending on the type
	 * (employee or type of customer).
	 * 
	 * @param personCode
	 * @param type
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String type, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
		if (personCode == null || personCode.isEmpty() || type == null || type.isEmpty() || 
				firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() ||
				street == null || street.isEmpty() || city == null || city.isEmpty() ||
				state == null || state.isEmpty() || zip == null || zip.isEmpty() || 
				country == null || country.isEmpty()) {
			throw new IllegalArgumentException("Please provide a valid arguments");
		}
		if (!validPersonType(type)) {
			throw new IllegalArgumentException("Please provide a valid person type.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		
		int personId = getId("Person", personCode);
		if (personId != -1) {
			throw new IllegalArgumentException("Person already exists in the database");
		}
		
		int addressId = getAddressId(street, city, zip, state, country);
		if (addressId == -1) {
			addressId = addAddress(street, city, zip, state, country);
		}
		
		PreparedStatement ps = null;
		
		String personQuery = "INSERT INTO Person(code, type, firstName, lastName, addressId) values (?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(personQuery);
			ps.setString(1, personCode);
			ps.setString(2, type);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			ps.setInt(5, addressId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		if (personCode == null || personCode.isEmpty() || email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		
		int personId = getId("Person", personCode);
		if (personId == -1) {
			throw new IllegalArgumentException("Person provided does not exist!");
		}
		
		int emailId = getId("Email", email);
		if (emailId != -1) {
			throw new IllegalArgumentException("Email already exists!");
		}
		
		String query = "INSERT INTO Email(name, personId) values (?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setInt(2, personId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 * 
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		if (storeCode == null || storeCode.isEmpty() || managerCode == null || managerCode.isEmpty() || 
				street == null || street.isEmpty() || city == null || city.isEmpty() || 
				state == null || state.isEmpty() || zip == null || zip.isEmpty() ||
				country == null || country.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int storeId = getId("Store", storeCode);
		if (storeId != -1) {
			throw new IllegalArgumentException("Store provided already exists!");
		}
		
		int addressId = getAddressId(street, city, zip, state, country);
		if (addressId == -1) {
			addressId = addAddress(street, city, zip, state, country);
		}
		
		int personId = getId("Person", managerCode);
		if (personId == -1) {
			throw new IllegalArgumentException("Please provide an existing manager.");
		}
		
		String query = "INSERT INTO Store(code, personId, addressId) values (?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			ps.setInt(2, personId);
			ps.setInt(3, addressId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a sales item (product, service, subscription) record to the database
	 * with the given <code>name</code> and <code>basePrice</code>. The type of item
	 * is specified by the <code>type</code> which may be one of "PN", "PU", "PG",
	 * "SV", or "SB". These correspond to new products, used products, gift cards
	 * (for which <code>basePrice</code> will be <code>null</code>), services, and
	 * subscriptions.
	 * 
	 * @param itemCode
	 * @param type
	 * @param name
	 * @param basePrice
	 */
	public static void addItem(String itemCode, String type, String name, Double basePrice) {
		if (itemCode == null || itemCode.isEmpty() || name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		if (!validItemType(type)) {
			throw new IllegalArgumentException("Please provide a valid item type.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int itemId = getId("Item", itemCode);
		if (itemId != -1) {
			throw new IllegalArgumentException("Item provided already exists!");
		}
		
		String query = null;
		
		if (type.equals("PN") || type.equals("PU") || type.equals("PG")) {
			query = "INSERT INTO Item(type, name, code, basePrice) values (?, ?, ?, ?);";
		}
		else if (type.equals("SV")) {
			query = "INSERT INTO Item(type, name, code, hourlyRate) values (?, ?, ?, ?);";
		}
		else if (type.equals("SB")) {
			query = "INSERT INTO Item(type, name, code, annualFee) values (?, ?, ?, ?);";
		}
		else {
			throw new IllegalArgumentException("Please provide a valid item type.");
		}
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, type);
			ps.setString(2, name);
			ps.setString(3, itemCode);
			if (basePrice == null) {
				ps.setNull(4, Types.DOUBLE);
			}
			else {
				ps.setDouble(4, basePrice);
			}
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a sales record to the database with the given data.
	 * 
	 * @param saleCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 */
	public static void addSale(String saleCode, String storeCode, String customerCode, String salesPersonCode) {
		if (saleCode == null || saleCode.isEmpty() || storeCode == null || storeCode.isEmpty() || customerCode == null || 
				customerCode.isEmpty() || salesPersonCode == null || salesPersonCode.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int saleId = getId("Sale", saleCode);
		if (saleId != -1) {
			throw new IllegalArgumentException("Sale provided already exists!");
		}
		
		int storeId = getId("Store", storeCode);
		if (storeId == -1) {
			throw new IllegalArgumentException("Please provide an existing store.");
		}
		
		int customerId = getId("Person", customerCode);
		if (customerId == -1) {
			throw new IllegalArgumentException("Please provide an existing customer.");
		}
		
		int salespersonId = getId("Person", salesPersonCode);
		if (salespersonId == -1) {
			throw new IllegalArgumentException("Please provide an existing salesperson.");
		}
		
		String query = "INSERT INTO Sale(code, storeId, customerId, salespersonId) values (?, ?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			ps.setInt(2, storeId);
			ps.setInt(3, customerId);
			ps.setInt(4, salespersonId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular product (new or used, identified by <code>itemCode</code>)
	 * to a particular sale record (identified by <code>saleCode</code>) with the
	 * specified quantity.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToSale(String saleCode, String itemCode, int quantity) {
		if (saleCode == null || saleCode.isEmpty() || itemCode == null || itemCode.isEmpty() || 
				quantity < 0) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int saleId = getId("Sale", saleCode);
		if (saleId == -1) {
			throw new IllegalArgumentException("Please provide an existing sale.");
		}
		
		int itemId = getId("Item", itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Please provide an existing product.");
		}
		
		String itemType = getItemType(itemCode);
		
		if (!(itemType.equals("PN") || itemType.equals("PU"))) {
			throw new IllegalArgumentException("Please provide a new or used product item.");
		}

		String query = "INSERT INTO SaleItem(quantity, saleId, itemId) values (?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, quantity);
			ps.setInt(2, saleId);
			ps.setInt(3, itemId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular gift card (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) in the specified
	 * amount.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addGiftCardToSale(String saleCode, String itemCode, double amount) {
		if (saleCode == null || saleCode.isEmpty() || itemCode == null || itemCode.isEmpty() || 
				amount < 0 ) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int saleId = getId("Sale", saleCode);
		if (saleId == -1) {
			throw new IllegalArgumentException("Please provide an existing sale.");
		}
		
		int itemId = getId("Item", itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Please provide an existing product.");
		}
		
		String itemType = getItemType(itemCode);
		if (!(itemType.equals("PG"))) {
			throw new IllegalArgumentException("Please provide a gift card.");
		}

		String query = "INSERT INTO SaleItem(giftCardPrice, saleId, itemId) values (?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setDouble(1, amount);
			ps.setInt(2, saleId);
			ps.setInt(3, itemId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * will be performed by the given employee for the specified number of
	 * hours.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param employeeCode
	 * @param billedHours
	 */
	public static void addServiceToSale(String saleCode, String itemCode, String employeeCode, double billedHours) {
		if (saleCode == null || saleCode.isEmpty() || itemCode == null || itemCode.isEmpty() || 
				employeeCode == null || employeeCode.isEmpty() || billedHours < 0 ) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int saleId = getId("Sale", saleCode);
		if (saleId == -1) {
			throw new IllegalArgumentException("Please provide an existing sale.");
		}
		
		int itemId = getId("Item", itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Please provide an existing product.");
		}
		
		int employeeId = getId("Person", employeeCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Please provide an existing employee.");
		}
		
		String itemType = getItemType(itemCode);
		if (!(itemType.equals("SV"))) {
			throw new IllegalArgumentException("Please provide a service.");
		}

		String query = "INSERT INTO SaleItem(personId, numberOfHours, saleId, itemId) values (?, ?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setDouble(2, billedHours);
			ps.setInt(3, saleId);
			ps.setInt(4, itemId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular subscription (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * is effective from the <code>startDate</code> to the <code>endDate</code>
	 * inclusive of both dates.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addSubscriptionToSale(String saleCode, String itemCode, String startDate, String endDate) {
		if (saleCode == null || saleCode.isEmpty() || itemCode == null || itemCode.isEmpty() || 
				startDate == null | startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		int saleId = getId("Sale", saleCode);
		if (saleId == -1) {
			throw new IllegalArgumentException("Please provide an existing sale.");
		}
		
		int itemId = getId("Item", itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Please provide an existing product.");
		}
		
		String itemType = getItemType(itemCode);
		if (!(itemType.equals("SB"))) {
			throw new IllegalArgumentException("Please provide a subscription.");
		}

		String query = "INSERT INTO SaleItem(beginDate, endDate, saleId, itemId) values (?, ?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setInt(3, saleId);
			ps.setInt(4, itemId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Empties a provided table name of all of its data.
	 * @param table
	 */
	private static void emptyTable(String table) {
		
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		
		String query = stringToDeleteQuery(table);
		
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a particular address to the database with the provided data and
	 * returns the addressId.
	 * @param street
	 * @param city
	 * @param zipCode
	 * @param state
	 * @param country
	 */
	private static int addAddress(String street, String city, String zip, String state, String country) {
		int stateId = -1;
		int countryId = -1;
		int addressId = -1;
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		stateId = getId("State", state);
		if (stateId == -1) {
			stateId = addState(state);
		}
		countryId = getId("Country", country);
		if (countryId == -1) {
			countryId = addCountry(country);
		}
		
		String addressQuery = "INSERT INTO Address (street, city, zipcode, stateId, countryId) values (?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.setInt(4, stateId);
			ps.setInt(5, countryId);
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			addressId = keys.getInt(1);
			keys.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return addressId;
	}
	
	private static int addState(String state) {
		int stateId = -1;
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		String stateQuery = "INSERT INTO State(name) values (?)";
		try {
			ps = conn.prepareStatement(stateQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, state);
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			stateId = keys.getInt(1);
			keys.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return stateId;
	}
	
	private static int addCountry(String country) {
		int countryId = -1;
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		
		String countryQuery = "INSERT INTO Country(name) values (?)";
		try {
			ps = conn.prepareStatement(countryQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, country);
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			countryId = keys.getInt(1);
			keys.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return countryId;
	}
	
	/**
	 * Gets the address id from the database matching the inputted information in the parameters.
	 * @param street
	 * @param city
	 * @param zip
	 * @param state
	 * @param country
	 * @return
	 */
	private static int getAddressId(String street, String city, String zip, String state, String country) {
		int returnId = -1;
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT addressId from Address where street = ? AND city = ? AND zipcode = ? AND "
				+ "stateId = (SELECT stateId from State where name = ?) "
				+ "AND countryId = (SELECT countryId from Country where name = ?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.setString(4, state);
			ps.setString(5, country);
			rs = ps.executeQuery();
			if(rs.next()) {
				returnId = rs.getInt("addressId");
			}
			else {
				returnId = -1;
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return returnId;
	}
	
	/**
	 * Checks if the provided string is a valid table in the database.
	 * @param table
	 */
	private static boolean validTable(String table) {
		if (table.equals("SaleItem") || table.equals("Item") || table.equals("Sale") ||
				table.equals("Store") || table.equals("Salesperson") || table.equals("Customer") ||
				table.equals("Email") || table.equals("Person") || table.equals("Address") ||
				table.equals("Country") || table.equals("State")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if the provided string is a valid person type.
	 * @param type
	 */
	private static boolean validPersonType(String type) {
		if (type.equals("E") || type.equals("G") || type.equals("P") || type.equals("C")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if the provided string is a valid item type.
	 * @param type
	 */
	private static boolean validItemType(String type) {
		//"PN", "PU", "PG","SV", or "SB".
		if (type.equals("PN") || type.equals("PU") || type.equals("PG") || type.equals("SV") || type.equals("SB")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Converts a provided string into a delete from table query.
	 * @param table
	 */
	private static String stringToDeleteQuery(String table) {
		if (!validTable(table)) {
			throw new IllegalArgumentException("Please provide a valid table.");
		}
		
		return "DELETE FROM " + table;
	}
	
	/**
	 * Gets the id from the database for Person, Store, Item, Sale, State, and Country
	 * based on the table and key provided.
	 * @param table
	 * @param key
	 * @return
	 */
	private static int getId(String table, String key) {
		if (!validTable(table)) {
			throw new IllegalArgumentException("Please provide a valid table.");
		}
		
		String returnIdString = null;
		int returnId = -1;
		
		Connection conn = DatabaseInfo.connectToDatabase();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = null;
		if (table.equals("Person")) {
			query = "SELECT personId from Person where code = ?;";
			returnIdString = "personId";
		}
		else if (table.equals("Store")) {
			query = "SELECT storeId from Store where code = ?;";
			returnIdString = "storeId";
		}
		else if (table.equals("Item")) {
			query = "SELECT itemId from Item where code = ?;";
			returnIdString = "itemId";
		}
		else if (table.equals("Sale")) {
			query = "SELECT saleId from Sale where code = ?;";
			returnIdString = "saleId";
		}
		else if (table.equals("State")) {
			query = "SELECT stateId from State where name = ?;";
			returnIdString = "stateId";
		}
		else if (table.equals("Country")) {
			query = "SELECT countryId from Country where name = ?;";
			returnIdString = "countryId";
		}
		else if (table.equals("Email")) {
			query = "SELECT emailId from Email where name = ?;";
			returnIdString = "emailId";
		}
		else {
			return returnId;
		}
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, key);
			rs = ps.executeQuery();
			if(rs.next()) {
				returnId = rs.getInt(returnIdString);
			}
			else {
				returnId = -1;
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return returnId;
	}
	
	/**
	 * Finds the item type of the provided item code within the database.
	 * @param itemCode
	 * @return type
	 */
	private static String getItemType(String itemCode) {
		if (itemCode == null || itemCode.isEmpty()) {
			throw new IllegalArgumentException("Please provide valid arguments.");
		}
		
		String type = null;
		
		Connection conn = DatabaseInfo.connectToDatabase();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select type from Item where code = ?;";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				type = rs.getString("type");
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return type;
	}

//	public static void main(String[] args) {
//		//public static void addProductToSale(String saleCode, String itemCode, int quantity)
//		addProductToSale("sAle001", "jc3jvi", 5);
//		System.out.println("Successful!");
//	}
}
