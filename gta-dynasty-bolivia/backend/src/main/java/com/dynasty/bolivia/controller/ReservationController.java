package com.dynasty.bolivia.controller;

import com.dynasty.bolivia.dto.ReservationRequest;
import com.dynasty.bolivia.model.Reservation;
import com.dynasty.bolivia.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public Reservation create(@RequestBody ReservationRequest request, Authentication auth) {
        return reservationService.createReservation(request, auth.getName());
    }

    @GetMapping("/reservations/me")
    public List<Reservation> myReservations(Authentication auth) {
        return reservationService.myReservations(auth.getName());
    }

    @GetMapping("/owner/reservations")
    public List<Reservation> ownerReservations(Authentication auth) {
        return reservationService.ownerReservations(auth.getName());
    }

    @PatchMapping("/reservations/{id}/cancel")
    public Reservation cancel(@PathVariable Long id, Authentication auth) {
        return reservationService.cancel(id, auth.getName());
    }
}
