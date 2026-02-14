package com.dynasty.bolivia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    @ManyToOne
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double total;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
