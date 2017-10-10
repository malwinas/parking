package com.malwinas.parking.model.repository;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malwinas.parking.model.Ticket;

/*
 * @author malwinas
 */
@Repository 
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	Collection<Ticket> findByEndTimeGreaterThanEqualAndEndTimeLessThanEqual(Timestamp start, Timestamp end);
	long countByRegistrationNumberAndStartTimeBeforeAndEndTimeIsNull(String registrationNumber, Timestamp time);
}
