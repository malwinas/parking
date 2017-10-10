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
		return getGeometricSequenceSum(2, 1, hours);
	}
	
	public Double getVipDriverCharge(Timestamp start, Timestamp end) {
		int hours = getHours(start, end);
		
		if (hours == 0 || hours == 1)
			return 0.0;
		
		return getGeometricSequenceSum(1.5, 2.0, hours - 1);
	}
	
	private int getHours(Timestamp start, Timestamp end) {
		if (end.before(start))
			return 0;
		
		long interval = end.getTime() - start.getTime();
		double hours = (double) interval / DateTimeConstants.MILLIS_PER_HOUR;
		
		return (int) Math.ceil(hours);
	}
	
	private double getGeometricSequenceSum(double ratio, double firstHour, int hours) {
		double sum = firstHour * (1 - Math.pow(ratio, hours))/(1 - ratio);
		return (Math.round(sum * 100) / 100.0);
	}
}
