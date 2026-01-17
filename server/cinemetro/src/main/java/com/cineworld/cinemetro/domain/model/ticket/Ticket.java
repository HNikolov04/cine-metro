package com.cineworld.cinemetro.domain.model.ticket;

import com.cineworld.cinemetro.domain.model.cinema.Seat;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"screening_id", "seat_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    private BigDecimal price;
}
