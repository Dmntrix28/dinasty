package com.dynasty.bolivia.dto;

import com.dynasty.bolivia.model.PropertyMode;
import com.dynasty.bolivia.model.PropertyType;
import lombok.Data;

import java.util.List;

@Data
public class PropertyRequest {
    private String titulo;
    private String descripcion;
    private PropertyType tipo;
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
    private List<String> imagenes;
    private Boolean disponible;
}
