package com.malwinas.parking.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @author malwinas
 */
@Entity
@Table(name = "ticket")
public class Ticket {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "registration_number", nullable = false)
	private String registrationNumber;
	
	@Column(name = "start_time", nullable = false)
	private Timestamp startTime;
	
	@Column(name = "vip_driver", nullable = false)
	private Boolean vipDriver;
	
	@Column(name = "end_time")
	private Timestamp endTime;
	
	@Column(name = "charge")
	private Double charge;
	
	public Ticket(String registrationNumber, Timestamp startTime, Boolean vipDriver) {
		this.registrationNumber = registrationNumber;
		this.startTime = startTime;
		this.vipDriver = vipDriver;
	}
	
	public Ticket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Boolean getVipDriver() {
		return vipDriver;
	}

	public void setVipDriver(Boolean vipDriver) {
		this.vipDriver = vipDriver;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}
}
