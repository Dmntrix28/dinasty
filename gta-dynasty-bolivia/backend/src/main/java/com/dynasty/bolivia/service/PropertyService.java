package com.dynasty.bolivia.service;

import com.dynasty.bolivia.dto.PropertyRequest;
import com.dynasty.bolivia.model.Property;
import com.dynasty.bolivia.model.PropertyMode;
import com.dynasty.bolivia.model.User;
import com.dynasty.bolivia.repository.PropertyRepository;
import com.dynasty.bolivia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Property getById(Long id) {
        return propertyRepository.findById(id).orElseThrow();
    }

    public Property create(PropertyRequest request, String userEmail) {
        User owner = userRepository.findByEmail(userEmail).orElseThrow();
        Property property = mapRequest(new Property(), request);
        property.setOwner(owner);
        property.setCreatedAt(LocalDateTime.now());
        if (property.getCiudad() == null || property.getCiudad().isBlank()) property.setCiudad("La Paz");
        if (property.getDisponible() == null) property.setDisponible(true);
        return propertyRepository.save(property);
    }

    public Property update(Long id, PropertyRequest request, String userEmail) {
        Property existing = getById(id);
        if (!existing.getOwner().getEmail().equals(userEmail)) throw new RuntimeException("No autorizado");
        return propertyRepository.save(mapRequest(existing, request));
    }

    public void delete(Long id, String userEmail) {
        Property existing = getById(id);
        if (!existing.getOwner().getEmail().equals(userEmail)) throw new RuntimeException("No autorizado");
        propertyRepository.delete(existing);
    }

    public List<Property> search(String modalidad, Double minPrice, Double maxPrice, String tipo, Integer dormitorios, String zona, String q, String sort) {
        return propertyRepository.findAll().stream()
                .filter(p -> modalidad == null || p.getModalidad().name().equalsIgnoreCase(modalidad) || p.getModalidad() == PropertyMode.AMBOS)
                .filter(p -> tipo == null || p.getTipo().name().equalsIgnoreCase(tipo))
                .filter(p -> dormitorios == null || (p.getDormitorios() != null && p.getDormitorios() >= dormitorios))
                .filter(p -> zona == null || p.getZona().toLowerCase().contains(zona.toLowerCase()))
                .filter(p -> q == null || (p.getTitulo()+" "+p.getDescripcion()).toLowerCase().contains(q.toLowerCase()))
                .filter(p -> {
                    Double price = modalidad != null && modalidad.equalsIgnoreCase("ALQUILER") ? p.getPrecioNoche() : p.getPrecioVenta();
                    if (price == null) return true;
                    return (minPrice == null || price >= minPrice) && (maxPrice == null || price <= maxPrice);
                })
                .sorted(getComparator(sort, modalidad))
                .toList();
    }

    private Comparator<Property> getComparator(String sort, String modalidad) {
        if ("price_asc".equals(sort)) return Comparator.comparing(p -> getSortPrice(p, modalidad), Comparator.nullsLast(Double::compareTo));
        if ("price_desc".equals(sort)) return Comparator.comparing((Property p) -> getSortPrice(p, modalidad), Comparator.nullsLast(Double::compareTo)).reversed();
        return Comparator.comparing(Property::getCreatedAt, Comparator.nullsLast(LocalDateTime::compareTo)).reversed();
    }

    private Double getSortPrice(Property p, String modalidad) {
        if ("ALQUILER".equalsIgnoreCase(modalidad)) return p.getPrecioNoche();
        return p.getPrecioVenta() != null ? p.getPrecioVenta() : p.getPrecioNoche();
    }

    private Property mapRequest(Property p, PropertyRequest request) {
        p.setTitulo(request.getTitulo());
        p.setDescripcion(request.getDescripcion());
        p.setTipo(request.getTipo());
        p.setModalidad(request.getModalidad());
        p.setPrecioVenta(request.getPrecioVenta());
        p.setPrecioNoche(request.getPrecioNoche());
        p.setCiudad(request.getCiudad());
        p.setZona(request.getZona());
        p.setLat(request.getLat());
        p.setLng(request.getLng());
        p.setDormitorios(request.getDormitorios());
        p.setBanos(request.getBanos());
        p.setM2(request.getM2());
        p.setImagenes(request.getImagenes());
        p.setDisponible(request.getDisponible());
        return p;
    }
}
