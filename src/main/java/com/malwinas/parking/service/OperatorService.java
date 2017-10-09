package com.malwinas.parking.service;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malwinas.parking.model.repository.TicketRepository;

/*
 * @author malwinas
 */
@Service
public class OperatorService {
	
	private final TicketRepository ticketRepository;
 
    @Autowired
    public OperatorService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    
    public Boolean hasStartedParkingMeter(String registrationNumber) {
    	Timestamp time = new Timestamp(DateTime.now().getMillis());
    	long ticketCount = ticketRepository.countByRegistrationNumberAndStartTimeBeforeAndEndTimeIsNull(time);
    	
    	return ticketCount > 0;
    }
}
