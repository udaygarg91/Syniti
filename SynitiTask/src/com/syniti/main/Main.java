package com.syniti.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.json.simple.parser.ParseException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Set<String> invalidCustomerIds = Util.getInvalidCustomerIds("data.json");
		Util.printList(invalidCustomerIds);
	}
}
