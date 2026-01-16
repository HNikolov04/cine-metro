package com.cineworld.cinemetro.domain.model.placeholder;

import jakarta.persistence.*;
import lombok.*;

// Temporary
@Entity(name = "PlaceholderMovie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
}
