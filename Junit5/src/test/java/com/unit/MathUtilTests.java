package com.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MathUtilTests {

	MathUtils mathUtils;

	@BeforeEach
	void setup() {
		mathUtils = new MathUtils();
	}

	@Test
	@DisplayName("ADD METHOD")
	void addTest() {

		assertEquals(2, mathUtils.sum(1, 1), "This is the sum method");

	}

	@Test
	@DisplayName("SUBSTRACTION METHOD")
	void substratTest() {
		assertEquals(4, mathUtils.substract(6, 2), "This is substracton method");
	}
}
