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
@RequestMapping("/parking/owner")
public class OwnerController {
	@RequestMapping(value = "/getProfit", method = RequestMethod.GET)
	public ResponseEntity<Double> getCharge(@PathVariable("time") Long time) {
		return new ResponseEntity<Double>(HttpStatus.OK);
    }
}
