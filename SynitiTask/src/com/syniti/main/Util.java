package com.syniti.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.syniti.model.Customer;

public class Util {
	public static boolean validateCustomerData(JSONObject customerJson) {

		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";

		Pattern pattern = Pattern.compile(regex);
		if (customerJson.get("zip") == null || !pattern.matcher(customerJson.get("zip").toString()).matches()) {
			return false;
		}
		if (customerJson.get("id") == null || customerJson.get("id").toString().trim().length() == 0) {
			return false;
		}
		if (customerJson.get("name") == null || customerJson.get("name").toString().trim().length() == 0) {
			return false;
		}
		if (customerJson.get("address") == null || customerJson.get("address").toString().trim().length() == 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static Set<String> getInvalidCustomerIds(String filePath)
			throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();

		JSONArray jsonArr = (JSONArray) parser.parse(new FileReader(filePath));
		// Customer object hashcode will be key and customer id will be value
		Map<Integer, Object> hashCodeIdMap = new HashMap<>();
		// To store invalid customer id in the list against null key. So we need to
		// traverse only on invalid id list to print in the end.
		hashCodeIdMap.put(null, new HashSet<String>());
		for (Object obj : jsonArr) {
			JSONObject customerJson = (JSONObject) obj;
			if (!validateCustomerData(customerJson)) {
				// add customer id in invalid id list against null key
				((Set<String>) hashCodeIdMap.get(null)).add(customerJson.get("id").toString());
				continue;
			}
			Customer customer = new Customer();
			customer.setId(customerJson.get("id").toString());
			customer.setName(customerJson.get("name").toString());
			customer.setAddress(customerJson.get("address").toString());
			customer.setPinCode(customerJson.get("zip").toString());
			if (hashCodeIdMap.keySet().contains(customer.hashCode())) {
				// If duplicate hashcode then add it in the invalid customer id list against
				// null key
				((Set<String>) hashCodeIdMap.get(null)).add(customer.getId());
				if (hashCodeIdMap.get(customer.hashCode()) != null) {
					// if first duplicate hashcode found for particular customer then add previous
					// customer id in invalid id list
					// then mark previous hashcode value with null just not to add it again next
					// time(if 3rd duplication found) in the invalid list
					((Set<String>) hashCodeIdMap.get(null)).add(hashCodeIdMap.get(customer.hashCode()).toString());
					hashCodeIdMap.replace(customer.hashCode(), null);
				}
			} else {
				// add valid customer id
				hashCodeIdMap.put(customer.hashCode(), customer.getId());
			}
		}

		return (Set<String>) hashCodeIdMap.get(null);
	}

	public static void printList(Set<String> list) {
		for (String customerId : list) {
			System.out.println(customerId);
		}
	}
}
