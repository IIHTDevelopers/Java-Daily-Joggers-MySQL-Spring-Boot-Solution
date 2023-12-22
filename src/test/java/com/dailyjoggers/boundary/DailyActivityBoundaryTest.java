package com.dailyjoggers.boundary;

import static com.dailyjoggers.utils.TestUtils.boundaryTestFile;
import static com.dailyjoggers.utils.TestUtils.currentTest;
import static com.dailyjoggers.utils.TestUtils.testReport;
import static com.dailyjoggers.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.dailyjoggers.dto.DailyActivityDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class DailyActivityBoundaryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testUserIdNotNull() throws IOException {
		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setUserId(null);
		Set<ConstraintViolation<DailyActivityDTO>> violations = validator.validate(dailyActivityDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDateNotNull() throws IOException {
		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setDate(null);
		Set<ConstraintViolation<DailyActivityDTO>> violations = validator.validate(dailyActivityDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStepsNotNull() throws IOException {
		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setSteps(0);
		Set<ConstraintViolation<DailyActivityDTO>> violations = validator.validate(dailyActivityDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDistanceNotNull() throws IOException {
		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setDistance(0);
		Set<ConstraintViolation<DailyActivityDTO>> violations = validator.validate(dailyActivityDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
