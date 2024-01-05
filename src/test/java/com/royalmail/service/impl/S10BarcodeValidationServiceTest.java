package com.royalmail.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.royalmail.exception.BarcodeValidationAPIException;
import com.royalmail.model.BarcodeValidationModel;

@SpringBootTest
class S10BarcodeValidationServiceTest {

	@InjectMocks
	private S10BarcodeValidationService service;

	@Mock
	private Environment environment;

	@Value("${test.data.invalid.barcode}")
	private String invalidBarcode;

	@Value("${test.data.valid.barcode}")
	private String validBarcode;
	
	@Value("${test.data.incorrect.barcode.format}")
	private String incorrectBarcode;

	@Test
	void testIsBarcodeValid() {

		BarcodeValidationModel response = service.isBarcodeValid(validBarcode);
		assertTrue(response.getIsValid());

	}
	
	@Test
	void testIsBarcodeInvalid() {

		BarcodeValidationModel response = service.isBarcodeValid(invalidBarcode);
		assertFalse(response.getIsValid());

	}

	@Test
	void shouldThrowBarcodeValidationAPIExceptionWhenIncorrectBarcodeFormat() {
		when(environment.getProperty("error.barcode.validation.api.incorrect.format"))
			.thenReturn("Barcode format is incorrect");
		
		assertThrows(BarcodeValidationAPIException.class, 
				() -> service.isBarcodeValid(incorrectBarcode));
	}

}
