package com.malwinas.parking.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.malwinas.parking.model.repository.TicketRepository;
import com.malwinas.parking.service.OperatorService;

/*
 * @author malwinas
 */
@RunWith(MockitoJUnitRunner.class)
public class OperatorControllerMvcTest {
	
	@Mock
	private TicketRepository ticketRepository;
	
	private MockMvc mockMvc;
	
	private final String registrationNumber = "WWW12345";
	@Before
    public void setUp() {
    	OperatorService driverService = new OperatorService(ticketRepository);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OperatorController(driverService))
                .build();
    }
	
	@Test
    public void hasStartedParkingMeterTest() throws Exception {	
    	long count = 1;
    	when(ticketRepository.countByRegistrationNumberAndStartTimeBeforeAndEndTimeIsNull(eq(registrationNumber), any(Timestamp.class)))
    		.thenReturn(count);
    	
    	String response = mockMvc
    				.perform(get("/parking/operator/hasStartedParkingMeter/{registrationNumber}", registrationNumber))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	verify(ticketRepository).countByRegistrationNumberAndStartTimeBeforeAndEndTimeIsNull(eq(registrationNumber), any(Timestamp.class));
    	Assert.assertTrue(new Boolean(response));
	}
	
	@Test
    public void hasNotStartedParkingMeterTest() throws Exception {	
    	long count = 0;
    	when(ticketRepository.countByRegistrationNumberAndStartTimeBeforeAndEndTimeIsNull(eq(registrationNumber), any(Timestamp.class)))
    		.thenReturn(count);
    	
    	String response = mockMvc
    				.perform(get("/parking/operator/hasStartedParkingMeter/{registrationNumber}", registrationNumber))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	verify(ticketRepository).countByRegistrationNumberAndStartTimeBeforeAndEndTimeIsNull(eq(registrationNumber), any(Timestamp.class));
    	Assert.assertFalse(new Boolean(response));
	}
}
