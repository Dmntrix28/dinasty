package com.dynasty.bolivia.controller;

import com.dynasty.bolivia.dto.PropertyRequest;
import com.dynasty.bolivia.model.Property;
import com.dynasty.bolivia.service.PropertyService;
import com.dynasty.bolivia.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService propertyService;
    private final TransactionService transactionService;

    public PropertyController(PropertyService propertyService, TransactionService transactionService) {
        this.propertyService = propertyService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Property> all() { return propertyService.getAll(); }

    @GetMapping("/{id}")
    public Property byId(@PathVariable Long id) { return propertyService.getById(id); }

    @GetMapping("/search")
    public List<Property> search(
            @RequestParam(required = false) String modalidad,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Integer dormitorios,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) String q,
            @RequestParam(required = false, defaultValue = "newest") String sort
    ) {
        return propertyService.search(modalidad, minPrice, maxPrice, tipo, dormitorios, zona, q, sort);
    }

    @PostMapping
    public Property create(@RequestBody PropertyRequest request, Authentication auth) {
        return propertyService.create(request, auth.getName());
    }

    @PutMapping("/{id}")
    public Property update(@PathVariable Long id, @RequestBody PropertyRequest request, Authentication auth) {
        return propertyService.update(id, request, auth.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        propertyService.delete(id, auth.getName());
    }

    @PostMapping("/{id}/buy")
    public Object buy(@PathVariable Long id, Authentication auth) {
        return transactionService.buyProperty(id, auth.getName());
    }
}
