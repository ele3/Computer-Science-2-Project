package com.mgg.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class contains the information in regards to accessing the database and utilizes
 * a connection factory to connect to the database.
 * 
 * Date: 04/05/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class DatabaseInfo {
	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static final String USERNAME = "etle";
	public static final String PASSWORD = "t3j-EW";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;
	
	public static Connection connectToDatabase() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return conn;
	}
}
