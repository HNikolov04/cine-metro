package com.cineworld.cinemetro.domain.model.placeholder;

import jakarta.persistence.*;
import lombok.*;

// Temporary
@Entity(name = "PlaceholderSeat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
}
