package vet360.address.validation.service.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vet360.address.validation.service.StubAddressValidatorApplication;
import vet360.address.validation.service.dio.Address;

@RestController
public class ValidatorController {

	@RequestMapping("/")
	public String index() {
		return "Spectrum Stub Service";
	}
	
	@RequestMapping(value = "validate", 
	        method=RequestMethod.POST, 
	        produces = {"application/json", "application/xml"})
	public String getResponse(@RequestBody Address address) {
		
		return StubAddressValidatorApplication.getResponseMessage(address.getStateCode());
	}
	
}
