package com.malwinas.parking.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Service;

/*
 * @author malwinas
 */
@Service
public class ChargeService {
	
	public BigDecimal getDriverCharge(Timestamp start, Timestamp end, Boolean isVipDriver) {
		return isVipDriver ? getVipDriverCharge(start, end) : getRegularDriverCharge(start, end);
	}
	
	public BigDecimal getRegularDriverCharge(Timestamp start, Timestamp end) {
		int hours = getHours(start, end);
		return getGeometricSequenceSum(2, BigDecimal.ONE, hours);
	}
	
	public BigDecimal getVipDriverCharge(Timestamp start, Timestamp end) {
		int hours = getHours(start, end);
		
		if (hours == 0 || hours == 1)
			return BigDecimal.ZERO;
		
		return getGeometricSequenceSum(1.5, new BigDecimal(2), hours - 1);
	}
	
	private int getHours(Timestamp start, Timestamp end) {
		if (end.before(start))
			return 0;
		
		long interval = end.getTime() - start.getTime();
		double hours = (double) interval / DateTimeConstants.MILLIS_PER_HOUR;
		
		return (int) Math.ceil(hours);
	}
	
	private BigDecimal getGeometricSequenceSum(double ratio, BigDecimal firstHour, int hours) {
		double progress = (1 - Math.pow(ratio, hours))/(1 - ratio);
		return firstHour.multiply(new BigDecimal(progress));
	}
}
