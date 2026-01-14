package com.cineworld.cinemetro.persistence.repository.ticket;

import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
