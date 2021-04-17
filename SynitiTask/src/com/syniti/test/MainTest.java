package com.syniti.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.syniti.main.Util;

class MainTest {

	@Test
	void blankNullMissingName() {
		try {
			Set<String> customerIds = Util.getInvalidCustomerIds("blankNullMissingName.json");
			assertEquals(customerIds.size(), 3);
			assertTrue("Missing Name", customerIds.contains("0bb47"));
			assertTrue("Null Name", customerIds.contains("ea651"));
			assertTrue("Blank Name", customerIds.contains("439a2"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	void blankNullMissingAddress() {
		try {
			Set<String> customerIds = Util.getInvalidCustomerIds("blankNullMissingAddress.json");
			assertEquals(customerIds.size(), 3);
			assertTrue("Missing Address", customerIds.contains("0bb47"));
			assertTrue("Null Address", customerIds.contains("ea651"));
			assertTrue("Blank Address", customerIds.contains("439a2"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	void invalidNullMissingZip() {
		try {
			Set<String> customerIds = Util.getInvalidCustomerIds("invalidNullMissingZip.json");
			assertEquals(customerIds.size(), 3);
			assertTrue("Missing Zip", customerIds.contains("0bb47"));
			assertTrue("Null Zip", customerIds.contains("ea651"));
			assertTrue("Invalid Zip", customerIds.contains("439a2"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
