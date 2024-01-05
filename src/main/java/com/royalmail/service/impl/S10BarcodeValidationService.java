package com.royalmail.service.impl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.royalmail.exception.BarcodeValidationAPIException;
import com.royalmail.model.BarcodeValidationModel;
import com.royalmail.service.BarcodeValidationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class S10BarcodeValidationService implements BarcodeValidationService {

	@Autowired
	private Environment environment;

	// REGEX Pattern for S10 bar code to follow below rules
	// Initial 2 characters from A-Z
	// Next 8 digits to be from 00000000 to 99999999
	// Next digit to be a number from 0-9
	// Last 2 characters to be country code GB always
	private final String S10_BARCODE_REGEX = "^([A-Z]{2})(\\d{8})(\\d)(GB)";
	// Provided weight for calculating sum for check digit calculation
	private final int[] weight = { 8, 6, 4, 2, 3, 5, 9, 7 };

	/**
	 * Service method to check if the entered bar code is valid or not as per S10
	 * bar code norms
	 */
	@Override
	public BarcodeValidationModel isBarcodeValid(String barcode) {
		log.info("isBarcodeValid method(): Started");

		BarcodeValidationModel model = new BarcodeValidationModel();

		validateBarcodeFormat(barcode);

		boolean isValidBarcode = validateCheckDigit(barcode.substring(2, 10), barcode.substring(10, 11), model);
		model.setBarcode(barcode);
		model.setIsValid(isValidBarcode);

		log.info("isBarcodeValid method(): Ended");
		return model;
	}

	/**
	 * This method will validate the bar code format as per the REGEX for S10 Bar
	 * codes. This will throw error if the bar code format does not matches the
	 * REGEX pattern.
	 * 
	 * @param barcode
	 */
	private void validateBarcodeFormat(String barcode) {
		log.info("validateBarcodeFormat method(): Started");
		Pattern regexPattern = Pattern.compile(S10_BARCODE_REGEX);
		Matcher regexMatcher = regexPattern.matcher(barcode);
		if (!regexMatcher.matches()) {
			throw new BarcodeValidationAPIException(HttpStatus.BAD_REQUEST,
					environment.getProperty("error.barcode.validation.api.incorrect.format"));
		}
	}

	/**
	 * This method validates if the provided serial number in bar code confirms to
	 * the Check Digit provided or not and returns the result
	 * 
	 * @param serialNumber
	 * @param checkDigit
	 * @param model
	 * @return
	 */
	private boolean validateCheckDigit(String serialNumber, String checkDigit, BarcodeValidationModel model) {
		log.info("validateCheckDigit method(): Started");

		int[] serialNumberArray = Arrays.stream(serialNumber.split(""))
										.mapToInt(Integer::valueOf)
										.toArray();
		var sum = 0;

		for (var i = 0; i < weight.length; i++) {
			sum += weight[i] * (serialNumberArray[i]);
		}
		var calculatedCheckDigit = 11 - (sum % 11);
		// If check digit is 10, change to 0 or if check digit is 11, change to 5 or
		// else use check digit calculated
		calculatedCheckDigit = calculatedCheckDigit == 10 ? 0 : calculatedCheckDigit == 11 ? 5 : calculatedCheckDigit;

		log.info("validateCheckDigit method(): Ended");
		return calculatedCheckDigit == Integer.parseInt(checkDigit);

	}

}
