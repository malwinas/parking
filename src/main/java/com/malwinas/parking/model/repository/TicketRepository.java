package com.malwinas.parking.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malwinas.parking.model.Ticket;

/*
 * @author malwinas
 */
@Repository 
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
