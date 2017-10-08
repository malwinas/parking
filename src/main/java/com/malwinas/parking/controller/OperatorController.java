package com.malwinas.parking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author malwinas
 */
@RestController
@RequestMapping("/parking/operator")
public class OperatorController {
	
	@RequestMapping(value = "/hasStartedParking", method = RequestMethod.GET)
	public ResponseEntity<Boolean> getCharge(@PathVariable("registrationNumber") Long registrationNumber) {
		return new ResponseEntity<Boolean>(HttpStatus.OK);
    }
}
