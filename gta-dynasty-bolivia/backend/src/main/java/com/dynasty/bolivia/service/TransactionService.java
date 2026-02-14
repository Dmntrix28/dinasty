package com.dynasty.bolivia.service;

import com.dynasty.bolivia.model.Property;
import com.dynasty.bolivia.model.Transaction;
import com.dynasty.bolivia.model.User;
import com.dynasty.bolivia.repository.PropertyRepository;
import com.dynasty.bolivia.repository.TransactionRepository;
import com.dynasty.bolivia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public Transaction buyProperty(Long propertyId, String email) {
        User buyer = userRepository.findByEmail(email).orElseThrow();
        Property property = propertyRepository.findById(propertyId).orElseThrow();

        if (!property.getDisponible()) throw new RuntimeException("Propiedad no disponible");
        if (property.getPrecioVenta() == null) throw new RuntimeException("No disponible para compra");

        property.setDisponible(false);
        propertyRepository.save(property);

        return transactionRepository.save(Transaction.builder()
                .buyer(buyer)
                .property(property)
                .precio(property.getPrecioVenta())
                .fecha(LocalDateTime.now())
                .build());
    }

    public List<Transaction> getByBuyer(String email) {
        User buyer = userRepository.findByEmail(email).orElseThrow();
        return transactionRepository.findByBuyer(buyer);
    }
}
