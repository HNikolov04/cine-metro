package com.cineworld.cinemetro.persistence.repository.ticket;

import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByScreening_Id(Long screeningId);
}
