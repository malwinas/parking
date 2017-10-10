package com.malwinas.parking.service;

import java.sql.Timestamp;

import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Service;

/*
 * @author malwinas
 */
@Service
public class ChargeService {
	
	public Double getDriverCharge(Timestamp start, Timestamp end, Boolean isVipDriver) {
		return isVipDriver ? getVipDriverCharge(start, end) : getRegularDriverCharge(start, end);
	}
	
	public Double getRegularDriverCharge(Timestamp start, Timestamp end) {
		int hours = getHours(start, end);
		
		int q = 2;
		int a = 1;
		
		return a * (1 - Math.pow(q, hours))/(1 - q);
	}
	
	public Double getVipDriverCharge(Timestamp start, Timestamp end) {
		int hours = getHours(start, end);
		
		if (hours == 0 || hours == 1)
			return 0.0;
		
		double q = 1.5;
		double a = 2.0;
		
		double charge = a * (1 - Math.pow(q, hours - 1))/(1 - q);
		
		return (Math.round(charge * 100) / 100.0);
	}
	
	private int getHours(Timestamp start, Timestamp end) {
		if (end.before(start))
			return 0;
		
		long interval = end.getTime() - start.getTime();
		double hours = (double) interval / DateTimeConstants.MILLIS_PER_HOUR;
		
		return (int) Math.ceil(hours);
	}
}
