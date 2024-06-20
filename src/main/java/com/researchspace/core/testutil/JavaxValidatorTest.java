package com.researchspace.core.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Utility methods for testing Javax Validator annotations on a class.
 * PRovides a {@link Validator} instance to subclasses.
 *
 */
public abstract class JavaxValidatorTest {
	
	protected static Validator validator;
	Logger log = LoggerFactory.getLogger(JavaxValidatorTest.class);
	
	@BeforeClass
	public static void beforeClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();	
	}
	
	/**
	 * Validates an object and asserts it has the expectedErrors, and prints errors.
	 */
	protected void assertNErrors (Object toValidate, int  expectedErrors) {
		assertNErrors(toValidate, expectedErrors, true);
	}
	/**
	 * 
	 * @param toValidate
	 * @param expectedErrors
	 * @param printMessages optional whether to print error messages
	 */
	protected void assertNErrors(Object toValidate, int expectedErrors, boolean printMessages) {
		Set<ConstraintViolation<Object>> violations = validator.validate(toValidate);
		if (printMessages) {
			violations.stream().map(ConstraintViolation::getMessage).forEach(log::error);
		}

		int violationCount = violations.size();
		assertEquals(expectedErrors, violationCount);

	}
	/**
	 * Asserts zero errors, i.e the object is valid.
	 * @param toValidate
	 */
	protected void assertValid (Object toValidate) {
		assertNErrors(toValidate, 0, true);
	}
}
