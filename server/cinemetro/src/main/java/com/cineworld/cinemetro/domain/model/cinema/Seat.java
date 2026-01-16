package com.cineworld.cinemetro.domain.model.cinema;

import com.cineworld.cinemetro.domain.enums.cinema.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"hall_id", "row_number", "seat_number"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false, length = 20)
    private SeatType seatType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;
}
