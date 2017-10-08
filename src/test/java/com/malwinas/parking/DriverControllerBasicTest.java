package com.malwinas.parking;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.malwinas.parking.controller.DriverController;
import com.malwinas.parking.controller.object.Parking;
import com.malwinas.parking.service.DriverService;

@RunWith(MockitoJUnitRunner.class)
public class DriverControllerBasicTest {
	
	@Mock
	private DriverService driverService;
	
	@InjectMocks
	private DriverController driverController;
	
	@Test
	public void startParkingTest() throws Exception {
		Parking parking = new Parking("WWW12345", false);
		Long ticketId = new Long(1);
		
		when(driverService.startParking(parking)).thenReturn(ticketId);
		
		ResponseEntity<Long> response = driverController.startParking(parking);
		
		verify(driverService).startParking(parking);
		Assert.assertEquals(ticketId, response.getBody());
	}
	
}
