package com.malwinas.parking.exception;

/*
 * @author malwinas
 */
public class TicketNotFoundException extends Exception {
	private static final long serialVersionUID = 5968267565694330551L;
	private final Long ticketId;
	
	public TicketNotFoundException(Long ticketId) {
		this.ticketId = ticketId;
	}
	
	@Override
	public String getMessage() {
		return "Ticket not found exception: " + ticketId;
	}
}
