package com.malwinas.parking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malwinas.parking.controller.object.Parking;
import com.malwinas.parking.model.repository.TicketRepository;
import com.malwinas.parking.service.ChargeService;
import com.malwinas.parking.service.DriverService;

/*
 * @author malwinas
 */
@RunWith(MockitoJUnitRunner.class)
public class DriverControllerInputParameterTest {
	
	@Mock
	private TicketRepository ticketRepository;
	 
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

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
	public void invalidRegistrationNumberTest() throws Exception {
		Parking parking = new Parking("", false);
		
		String json = objectMapper.writeValueAsString(parking);
		
		mockMvc.perform(post("/parking/driver/startParkingMeter")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void invalidIsVipDriverTest() throws Exception {
		Parking parking = new Parking("WWW12345", null);
		
		String json = objectMapper.writeValueAsString(parking);
		
		mockMvc.perform(post("/parking/driver/startParkingMeter")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
				.andExpect(status().isBadRequest());
	}
}
