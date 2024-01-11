package com.dailyjoggers.boundary;

import static com.dailyjoggers.utils.TestUtils.boundaryTestFile;
import static com.dailyjoggers.utils.TestUtils.currentTest;
import static com.dailyjoggers.utils.TestUtils.testReport;
import static com.dailyjoggers.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dailyjoggers.dto.UserDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserBoundaryTest {

	private static Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testUsernameNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(null);
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(null);
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testValidEmailFormat() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("invalid_email_format");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
