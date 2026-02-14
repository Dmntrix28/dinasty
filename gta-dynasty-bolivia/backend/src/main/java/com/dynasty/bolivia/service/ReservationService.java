package com.dynasty.bolivia.service;

import com.dynasty.bolivia.dto.ReservationRequest;
import com.dynasty.bolivia.model.*;
import com.dynasty.bolivia.repository.PropertyRepository;
import com.dynasty.bolivia.repository.ReservationRepository;
import com.dynasty.bolivia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public Reservation createReservation(ReservationRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Property property = propertyRepository.findById(request.getPropertyId()).orElseThrow();

        if (property.getPrecioNoche() == null) throw new RuntimeException("No disponible para alquiler");
        if (reservationRepository.hasOverlap(property, request.getStartDate(), request.getEndDate())) {
            throw new RuntimeException("Las fechas se solapan con una reserva existente");
        }
        long nights = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        if (nights <= 0) throw new RuntimeException("Fechas inválidas");

        Reservation reservation = Reservation.builder()
                .property(property)
                .user(user)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .total(nights * property.getPrecioNoche())
                .status(ReservationStatus.CONFIRMED)
                .build();
        return reservationRepository.save(reservation);
    }

    public List<Reservation> myReservations(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> ownerReservations(String email) {
        User owner = userRepository.findByEmail(email).orElseThrow();
        return reservationRepository.findByPropertyIn(propertyRepository.findByOwner(owner));
    }

    public Reservation cancel(Long id, String email) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (!reservation.getUser().getEmail().equals(email) && !reservation.getProperty().getOwner().getEmail().equals(email)) {
            throw new RuntimeException("No autorizado");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }
}
