package com.malwinas.parking.service;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malwinas.parking.controller.object.Parking;
import com.malwinas.parking.exception.InvalidTicketException;
import com.malwinas.parking.exception.TicketNotFoundException;
import com.malwinas.parking.model.Ticket;
import com.malwinas.parking.model.repository.TicketRepository;

/*
 * @author malwinas
 */
@Service
public class DriverService {
	
	private final TicketRepository ticketRepository;
	private final ChargeService chargeService;
 
    @Autowired
    public DriverService(TicketRepository ticketRepository, ChargeService chargeService) {
        this.ticketRepository = ticketRepository;
        this.chargeService = chargeService;
    }
	
    @Transactional
	public Long startParking(Parking parking) {
		Timestamp startTime = new Timestamp(DateTime.now().getMillis());
		Ticket ticket = new Ticket(parking.getRegistrationNumber(), startTime, parking.getIsVipDriver());
		
		return ticketRepository.save(ticket).getId();
	}
	
	@Transactional
	public void stopParking(Long ticketId) throws TicketNotFoundException, InvalidTicketException {
		Ticket ticket = ticketRepository.findOne(ticketId);
		
		if (ticket == null)
			throw new TicketNotFoundException(ticketId);
		
		if (ticket.getEndTime() != null)
			throw new InvalidTicketException();
		
		Timestamp endTime = new Timestamp(DateTime.now().getMillis());
		Double charge = chargeService.getDriverCharge(ticket.getStartTime(), endTime, ticket.getVipDriver());
		
		ticket.setEndTime(endTime);
		ticket.setCharge(charge);
		ticketRepository.save(ticket);
	}
	
	@Transactional
	public Double getCharge(Long ticketId) throws TicketNotFoundException {
		Ticket ticket = ticketRepository.findOne(ticketId);
		
		if (ticket == null)
			throw new TicketNotFoundException(ticketId);
		
		if (ticket.getCharge() != null)
			return ticket.getCharge();
		
		Timestamp endTime = new Timestamp(DateTime.now().getMillis());
		Double charge = chargeService.getDriverCharge(ticket.getStartTime(), endTime, ticket.getVipDriver());
		
		return charge;
	}

}
