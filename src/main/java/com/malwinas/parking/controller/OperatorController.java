package com.malwinas.parking.controller;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malwinas.parking.service.OperatorService;

/*
 * @author malwinas
 */
@RestController
@RequestMapping("/parking/operator")
public class OperatorController {
	
	private final OperatorService operatorService;
	
	@Autowired
	public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }
	
	@RequestMapping(value = "/hasStartedParkingMeter", method = RequestMethod.GET)
	public ResponseEntity<Boolean> hasStartedParkingMeter(
			@RequestParam("registrationNumber") @Size(min = 8, max = 8) String registrationNumber) {
		Boolean hasStartedParkingmeter = operatorService.hasStartedParkingMeter(registrationNumber);
		return new ResponseEntity<Boolean>(hasStartedParkingmeter, HttpStatus.OK);
    }
}
