package com.dynasty.bolivia.repository;

import com.dynasty.bolivia.model.Transaction;
import com.dynasty.bolivia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBuyer(User buyer);
}
