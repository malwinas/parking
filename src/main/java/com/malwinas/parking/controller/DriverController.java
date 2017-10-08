package com.malwinas.parking.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.malwinas.parking.controller.object.Parking;
import com.malwinas.parking.exception.InvalidTicketException;
import com.malwinas.parking.exception.TicketNotFoundException;
import com.malwinas.parking.service.DriverService;

/*
 * @author malwinas
 */
@RestController
@RequestMapping("/parking/driver")
public class DriverController {
	private final Logger logger = Logger.getLogger(DriverController.class.getName());
	private final DriverService driverService;
	
	@Autowired
	public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
	
	@RequestMapping(value = "/startParking", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> startParking(@Valid @RequestBody Parking parking) {
		Long ticketId = driverService.startParking(parking);
		return new ResponseEntity<Long>(ticketId, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/stopParking", method = RequestMethod.POST)
	public void stopParking(@RequestBody Long ticketId) throws TicketNotFoundException, InvalidTicketException {
		driverService.stopParking(ticketId);
    }
	
	@RequestMapping(value = "/getCharge", method = RequestMethod.GET)
	public ResponseEntity<Double> getCharge(@PathVariable("ticketId") Long ticketId) {
		return new ResponseEntity<Double>(HttpStatus.OK);
    }
	
	@ExceptionHandler({TicketNotFoundException.class})
    public ResponseEntity<String> handleTicketNotFoundException(TicketNotFoundException ex) {
		logger.log(Level.WARNING, ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NO_CONTENT);
    } 
	
	@ExceptionHandler({InvalidTicketException.class})
    public ResponseEntity<String> handleInvalidTicketException(InvalidTicketException ex) {
		logger.log(Level.WARNING, ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
