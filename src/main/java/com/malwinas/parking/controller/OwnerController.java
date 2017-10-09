package com.malwinas.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malwinas.parking.service.OwnerService;

/*
 * @author malwinas
 */
@RestController
@RequestMapping("/parking/owner")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	@Autowired
	public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
	
	@RequestMapping(value = "/getProfit", method = RequestMethod.GET)
	public ResponseEntity<Double> getCharge(@RequestParam("time") Long time) {
		Double profit = ownerService.getProfit(time);
		return new ResponseEntity<Double>(profit, HttpStatus.OK);
    }
}
