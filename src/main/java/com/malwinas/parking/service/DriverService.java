package com.malwinas.parking.service;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 
    @Autowired
    public DriverService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
		
		if (ticket.getEndTime() != null || ticket.getCharge() != null)
			throw new InvalidTicketException();
		
		Timestamp endTime = new Timestamp(DateTime.now().getMillis());
		
		ticket.setEndTime(endTime);
		ticket.setCharge(10.00);
		ticketRepository.save(ticket);
	}

}