package com.cineworld.cinemetro.domain.model.cinema;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hall", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"building_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private CinemaBuilding building;

    @OneToMany(mappedBy = "hall", orphanRemoval = false)
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();
}
