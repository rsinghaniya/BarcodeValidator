package com.royalmail.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.royalmail.S10BarcodeValidatorApplication;
import com.royalmail.model.BarcodeValidationModel;

@SpringBootTest(classes = S10BarcodeValidatorApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class ValidateS10BarcodeControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private Environment environment;

	@Test
	void testIsBarcodeValid() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<BarcodeValidationModel> response = this.restTemplate
															.withBasicAuth(environment.getProperty("spring.security.user.name"), environment.getProperty("spring.security.user.password"))
															.exchange("http://localhost:" + port + "/api/validate?barcode=" + environment.getProperty("test.data.valid.barcode"), 
															HttpMethod.GET,	entity, new ParameterizedTypeReference<BarcodeValidationModel>(){});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(response.getBody().getIsValid());
		
	}

	@Test
	void testIsBarcodeInvalid() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<BarcodeValidationModel> response = this.restTemplate
															.withBasicAuth(environment.getProperty("spring.security.user.name"), environment.getProperty("spring.security.user.password"))
															.exchange("http://localhost:" + port + "/api/validate?barcode=" + environment.getProperty("test.data.invalid.barcode"), 
															HttpMethod.GET,	entity, new ParameterizedTypeReference<BarcodeValidationModel>(){});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertFalse(response.getBody().getIsValid());
		
	}

}
