package com.mgg.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mgg.entity.Person;
import com.mgg.entity.Store;
import com.mgg.entity.Item;
import com.mgg.reader.Parser;

/**
 * This class serializes the data from the CSV files gained from the 
 * Parser class into JSON output files.
 * 
 * Date: 02/26/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class JSONConverter {
	/**
	 * Converts the Person ArrayList into JSON form, and outputs as a JSON file.
	 */
	public static void jsonPersonOutput() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<Person> personData = Parser.parsePersonData();
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
	 * Converts the Store ArrayList into JSON form, and outputs as a JSON file.
	 */
	public static void jsonStoreOutput() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<Store> storeData = Parser.parseStoreData();
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
	 * Converts the Item ArrayList into JSON form, and outputs as a JSON file.
	 */
	public static void jsonItemOutput() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<Item> itemData = Parser.parseItemData();
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
}
