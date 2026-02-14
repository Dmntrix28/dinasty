package com.dynasty.bolivia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    @Column(length = 2000)
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private PropertyType tipo;
    @Enumerated(EnumType.STRING)
    private PropertyMode modalidad;
    private Double precioVenta;
    private Double precioNoche;
    private String ciudad;
    private String zona;
    private Double lat;
    private Double lng;
    private Integer dormitorios;
    private Integer banos;
    private Double m2;
    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imagenes = new ArrayList<>();
    private Boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    private LocalDateTime createdAt;
}
