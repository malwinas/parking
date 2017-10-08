package com.malwinas.parking;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

import org.joda.time.DateTime;
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
import com.malwinas.parking.service.DriverService;

@RunWith(MockitoJUnitRunner.class)
public class DriverControllerMvcTest {
	
	@Mock
	private TicketRepository ticketRepository;
	 
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

    @Before
    public void setUp() {
    	DriverService driverService = new DriverService(ticketRepository);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new DriverController(driverService))
                .build();
        objectMapper = new ObjectMapper();
        
    }
    
    @Test
    public void startParkingTest() throws Exception {
    	Parking parking = new Parking("WWW12345", false);
    	
    	Timestamp startTime = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Long ticketId = new Long(123);
		
    	Ticket ticket = new Ticket(parking.getRegistrationNumber(), startTime, parking.getIsVipDriver());
    	ticket.setId(ticketId);
    	
    	when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
    	
    	String json = objectMapper.writeValueAsString(parking);
    	
    	String response = mockMvc.perform(post("/parking/driver/startParking")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(json))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	verify(ticketRepository).save(any(Ticket.class));
    	Assert.assertEquals(ticketId.longValue(), new Long(response).longValue()); 
	}
    
    @Test
    public void invalidRegistrationNumberTest() throws Exception {
    	Parking parking = new Parking("", false);
    	
    	String json = objectMapper.writeValueAsString(parking);
    	
    	mockMvc.perform(post("/parking/driver/startParking")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(json))
    			.andExpect(status().isBadRequest());
	}
    
    @Test
    public void invalidIsVipDriverTest() throws Exception {
    	Parking parking = new Parking("WWW123456", null);
    	
    	String json = objectMapper.writeValueAsString(parking);
    	
    	mockMvc.perform(post("/parking/driver/startParking")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(json))
    			.andExpect(status().isBadRequest());
	}

    
}
