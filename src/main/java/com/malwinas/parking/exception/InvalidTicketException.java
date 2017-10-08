package com.malwinas.parking.exception;

/*
 * @author malwinas
 */
public class InvalidTicketException extends Exception {
	private static final long serialVersionUID = -1398677982679358731L;

	@Override
	public String getMessage() {
		return "Invalid ticket exception";
	}
}
