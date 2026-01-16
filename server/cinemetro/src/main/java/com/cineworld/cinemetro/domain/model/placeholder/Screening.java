package com.cineworld.cinemetro.domain.model.placeholder;

import jakarta.persistence.*;
import lombok.*;

// Temporary
@Entity(name = "PlaceholderScreening")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startTime; // temporary field, will later become LocalDateTime
}
