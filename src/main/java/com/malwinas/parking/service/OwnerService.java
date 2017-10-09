package com.malwinas.parking.service;

import java.sql.Timestamp;
import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malwinas.parking.model.Ticket;
import com.malwinas.parking.model.repository.TicketRepository;

/*
 * @author malwinas
 */
@Service
public class OwnerService {
	
	private final TicketRepository ticketRepository;
 
    @Autowired
    public OwnerService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
	
	public Double getProfit(Long time) {
		Timestamp startOfDay = new Timestamp(new DateTime(time).withTimeAtStartOfDay().getMillis());
		Timestamp endOfDay = new Timestamp(new DateTime(time).millisOfDay().withMaximumValue().getMillis());
		
		Collection<Ticket> tickets = ticketRepository.findByEndTimeAfterAndEndTimeBefore(startOfDay, endOfDay);
		
		Double profit = tickets.stream().mapToDouble(o -> o.getCharge()).sum();
		
		return profit;
	}

}
