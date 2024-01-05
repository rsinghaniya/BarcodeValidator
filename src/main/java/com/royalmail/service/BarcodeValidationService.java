package com.royalmail.service;

import com.royalmail.model.BarcodeValidationModel;

public interface BarcodeValidationService {
	
	public BarcodeValidationModel isBarcodeValid(String barcode);

}
