package com.malwinas.parking.controller.object;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Parking {
	
	@Size(min=8, max=8)
	private String registrationNumber;
	
	@NotNull
	private Boolean isVipDriver;
	
	@JsonCreator
	public Parking(@JsonProperty("registrationNumber") String registrationNumber, 
				   @JsonProperty("isVipDriver") Boolean isVipDriver) {
		this.registrationNumber = registrationNumber;
		this.isVipDriver = isVipDriver;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public Boolean getIsVipDriver() {
		return isVipDriver;
	}
}
