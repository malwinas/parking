package com.malwinas.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/*
 * @author malwinas
 */
@SpringBootApplication
public class ParkingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
		System.out.println("Hello world!");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ParkingApplication.class);
	}
}
