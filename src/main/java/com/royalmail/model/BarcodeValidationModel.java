package com.royalmail.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarcodeValidationModel {
	
	private String barcode;
	private Boolean isValid;

}
