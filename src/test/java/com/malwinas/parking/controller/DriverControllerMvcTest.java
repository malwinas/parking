package com.malwinas.parking.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malwinas.parking.controller.DriverController;
import com.malwinas.parking.controller.object.Parking;
import com.malwinas.parking.model.Ticket;
import com.malwinas.parking.model.repository.TicketRepository;
import com.malwinas.parking.service.ChargeService;
import com.malwinas.parking.service.DriverService;

/*
 * @author malwinas
 */
@RunWith(MockitoJUnitRunner.class)
public class DriverControllerMvcTest {
	
	@Mock
	private TicketRepository ticketRepository;
	 
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	private Long ticketId = new Long(1234);

    @Before
    public void setUp() {
    	ChargeService chargeService = new ChargeService();
    	DriverService driverService = new DriverService(ticketRepository, chargeService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new DriverController(driverService))
                .build();
        objectMapper = new ObjectMapper();
        
    }
    
    @Test
    public void startParkingMeterTest() throws Exception {
    	Ticket ticket = getTicket(ticketId);
    	
    	when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
    	
    	Parking parking = new Parking("WWW12345", false);
    	String json = objectMapper.writeValueAsString(parking);
    	
    	String response = mockMvc.perform(post("/parking/driver/startParkingMeter")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(json))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	verify(ticketRepository).save(any(Ticket.class));
    	
    	Assert.assertEquals(ticketId.longValue(), new Long(response).longValue());
	}

    @Test
    public void stopParkingMeterTest() throws Exception {	
    	when(ticketRepository.findOne(ticketId)).thenReturn(getTicket(ticketId));
    	
    	String json = objectMapper.writeValueAsString(ticketId);
    	
    	mockMvc.perform(post("/parking/driver/stopParkingMeter")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(json))
    			.andExpect(status().isOk());
    	
    	verify(ticketRepository).save(any(Ticket.class));
	}
    
    @Test
    public void getChargeTest() throws Exception {	
    	Long stopTime = DateTime.now().withTimeAtStartOfDay().plusHours(15).plusMinutes(10).getMillis();
    	
    	DateTimeUtils.setCurrentMillisFixed(stopTime);
    	when(ticketRepository.findOne(ticketId)).thenReturn(getTicket(ticketId));
    	
    	String response = mockMvc.perform(get("/parking/driver/getCharge")
    				.param("ticketId", ticketId.toString()))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	DateTimeUtils.setCurrentMillisSystem();
    	verify(ticketRepository).findOne(ticketId);
    	
    	Assert.assertEquals(255.0, new Double(response).doubleValue(), Math.pow(10, -2));
	}
    
    private Ticket getTicket(Long ticketId) {
    	Timestamp startTime = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		
    	Ticket ticket = new Ticket("WWW12345", startTime, false);
    	ticket.setId(ticketId);
    	
    	return ticket;
    }
}
