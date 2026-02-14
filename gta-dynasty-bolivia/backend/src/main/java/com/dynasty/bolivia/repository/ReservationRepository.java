package com.dynasty.bolivia.repository;

import com.dynasty.bolivia.model.Property;
import com.dynasty.bolivia.model.Reservation;
import com.dynasty.bolivia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByPropertyIn(List<Property> properties);
    List<Reservation> findByPropertyAndStatus(Property property, com.dynasty.bolivia.model.ReservationStatus status);

    default boolean hasOverlap(Property property, LocalDate startDate, LocalDate endDate) {
        return findByPropertyAndStatus(property, com.dynasty.bolivia.model.ReservationStatus.CONFIRMED)
            .stream().anyMatch(r -> !(endDate.isBefore(r.getStartDate()) || startDate.isAfter(r.getEndDate())));
    }
}
