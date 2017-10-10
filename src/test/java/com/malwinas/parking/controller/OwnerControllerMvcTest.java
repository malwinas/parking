package com.malwinas.parking.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.malwinas.parking.model.Ticket;
import com.malwinas.parking.model.repository.TicketRepository;
import com.malwinas.parking.service.OwnerService;

/*
 * @author malwinas
 */
@RunWith(MockitoJUnitRunner.class)
public class OwnerControllerMvcTest {
	
	@Mock
	private TicketRepository ticketRepository;
	
	private MockMvc mockMvc;
	
	@Before
    public void setUp() {
    	OwnerService ownerService = new OwnerService(ticketRepository);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OwnerController(ownerService))
                .build();
    }
	
	@Test
    public void getProfitTest() throws Exception {
		when(ticketRepository.findByEndTimeGreaterThanEqualAndEndTimeLessThanEqual(any(Timestamp.class), any(Timestamp.class)))
    		.thenReturn(getTickets());
    	
    	String response = mockMvc.perform(get("/parking/owner/getProfit")
    				.param("time", new Long(DateTime.now().getMillis()).toString()))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	verify(ticketRepository).findByEndTimeGreaterThanEqualAndEndTimeLessThanEqual(any(Timestamp.class), any(Timestamp.class));
    	
    	Assert.assertEquals(9.0, new Double(response).doubleValue(), Math.pow(10, -2));
	}
	
	@Test
    public void getNoProfitTest() throws Exception {
		when(ticketRepository.findByEndTimeGreaterThanEqualAndEndTimeLessThanEqual(any(Timestamp.class), any(Timestamp.class)))
    		.thenReturn(new ArrayList<Ticket>());
    	
    	String response = mockMvc.perform(get("/parking/owner/getProfit")
    				.param("time", new Long(DateTime.now().getMillis()).toString()))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	verify(ticketRepository).findByEndTimeGreaterThanEqualAndEndTimeLessThanEqual(any(Timestamp.class), any(Timestamp.class));
    	
    	Assert.assertEquals(0.0, new Double(response).doubleValue(), Math.pow(10, -2));
	}
	
	private Collection<Ticket> getTickets() {
		Timestamp firstStartTime = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(10).getMillis());
		Timestamp firstEndTime = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(13).getMillis());
		Ticket firstTicket = new Ticket("WWW12345", firstStartTime, false);
		firstTicket.setId(new Long(1));
		firstTicket.setEndTime(firstEndTime);
		firstTicket.setCharge(7.0);
		
		Timestamp secondStartTime = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(13).getMillis());
		Timestamp secondEndTime = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(15).getMillis());
		Ticket secondTicket = new Ticket("WWW54321", secondStartTime, true);
		secondTicket.setId(new Long(2));
		secondTicket.setEndTime(secondEndTime);
		secondTicket.setCharge(2.0);
		
		return new ArrayList<>(Arrays.asList(firstTicket, secondTicket));
	}
}
