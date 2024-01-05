package com.royalmail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.royalmail.model.BarcodeValidationModel;
import com.royalmail.service.BarcodeValidationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class ValidateS10BarcodeController {

	private BarcodeValidationService barcodeValidationService;

	public ValidateS10BarcodeController(BarcodeValidationService barcodeValidationService) {
		this.barcodeValidationService = barcodeValidationService;
	}

	@Operation(summary = "Validate the Barcode")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Valid or Invalid Barcode", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = BarcodeValidationModel.class)) }),
			@ApiResponse(responseCode = "400", description = "Barcode format is incorrect", content = @Content) })
	@GetMapping("/validate")
	public ResponseEntity<BarcodeValidationModel> isBarcodeValid(
			@RequestParam(name = "barcode", required = true) String barcode) {

		return ResponseEntity.ok(barcodeValidationService.isBarcodeValid(barcode));
	}

}
