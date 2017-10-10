package com.malwinas.parking.service;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.malwinas.parking.service.ChargeService;

/*
 * @author malwinas
 */
@RunWith(MockitoJUnitRunner.class)
public class ChargeServiceTest {
	
	@InjectMocks
	private ChargeService chargeService;
	
	@Test
	public void regularDriverEndBeforeStartTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(7).getMillis());
		
		Double charge = chargeService.getRegularDriverCharge(start, end);
		Assert.assertEquals(0.0, charge, Math.pow(10, -2));
	}
	
	@Test
	public void regularDriverChargeTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(9).getMillis());
		
		Double charge = chargeService.getRegularDriverCharge(start, end);
		Assert.assertEquals(1.0, charge, Math.pow(10, -2));
	}
	
	@Test
	public void regularDriverTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(14)
				.plusMinutes(30).getMillis());
		
		Double charge = chargeService.getRegularDriverCharge(start, end);
		Assert.assertEquals(127.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void vipDriverEndBeforeStartTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(7).getMillis());
		
		Double charge = chargeService.getVipDriverCharge(start, end);
		Assert.assertEquals(0.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void vipDriverChargeTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(9).getMillis());
		
		Double charge = chargeService.getVipDriverCharge(start, end);
		Assert.assertEquals(0.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void vipDriverTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(14).plusMinutes(30)
				.getMillis());
		
		Double charge = chargeService.getVipDriverCharge(start, end);
		Assert.assertEquals(41.56, charge.doubleValue(), Math.pow(10, -2));
	}
	
}
