package com.malwinas.parking.service;

import java.math.BigDecimal;
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
		
		BigDecimal charge = chargeService.getRegularDriverCharge(start, end);
		Assert.assertEquals(0.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void regularDriverDuringHourChargeTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(9).getMillis());
		
		BigDecimal charge = chargeService.getRegularDriverCharge(start, end);
		Assert.assertEquals(1.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void regularDriverDuring7HoursTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay()
				.plusHours(14).plusMinutes(30).getMillis());
		
		BigDecimal charge = chargeService.getRegularDriverCharge(start, end);
		Assert.assertEquals(127.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void vipDriverEndBeforeStartTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(7).getMillis());
		
		BigDecimal charge = chargeService.getVipDriverCharge(start, end);
		Assert.assertEquals(0.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void vipDriverDuringHourChargeTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(9).getMillis());
		
		BigDecimal charge = chargeService.getVipDriverCharge(start, end);
		Assert.assertEquals(0.0, charge.doubleValue(), Math.pow(10, -2));
	}
	
	@Test
	public void vipDriverDuring7HoursTest() {
		Timestamp start = new Timestamp(DateTime.now().withTimeAtStartOfDay().plusHours(8).getMillis());
		Timestamp end = new Timestamp(DateTime.now().withTimeAtStartOfDay()
				.plusHours(14).plusMinutes(30).getMillis());
		
		BigDecimal charge = chargeService.getVipDriverCharge(start, end);
		Assert.assertEquals(41.56, charge.doubleValue(), Math.pow(10, -2));
	}
	
}
