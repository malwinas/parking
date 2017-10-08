package com.malwinas.parking.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

/*
 * @author malwinas
 */
@Service
public class ChargeService {
	
	public Double getCharge(Timestamp start, Timestamp end, Boolean isVipDriver) {
		return 0.0;
	}
}
