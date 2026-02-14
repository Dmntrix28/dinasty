package com.dynasty.bolivia.repository;

import com.dynasty.bolivia.model.Property;
import com.dynasty.bolivia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwner(User owner);
}
