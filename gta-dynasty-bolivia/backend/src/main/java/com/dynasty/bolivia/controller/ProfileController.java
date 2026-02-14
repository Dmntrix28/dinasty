package com.dynasty.bolivia.controller;

import com.dynasty.bolivia.model.Property;
import com.dynasty.bolivia.model.Reservation;
import com.dynasty.bolivia.model.Transaction;
import com.dynasty.bolivia.model.User;
import com.dynasty.bolivia.repository.PropertyRepository;
import com.dynasty.bolivia.repository.UserRepository;
import com.dynasty.bolivia.service.ReservationService;
import com.dynasty.bolivia.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final TransactionService transactionService;
    private final ReservationService reservationService;

    public ProfileController(UserRepository userRepository, PropertyRepository propertyRepository, TransactionService transactionService, ReservationService reservationService) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.transactionService = transactionService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public Map<String, Object> profile(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        List<Property> properties = propertyRepository.findByOwner(user);
        List<Transaction> transactions = transactionService.getByBuyer(auth.getName());
        List<Reservation> reservations = reservationService.myReservations(auth.getName());
        return Map.of("user", user, "myPublications", properties, "myTransactions", transactions, "myReservations", reservations);
    }
}
