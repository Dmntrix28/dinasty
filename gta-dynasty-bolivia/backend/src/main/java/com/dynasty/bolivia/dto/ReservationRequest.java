package com.dynasty.bolivia.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequest {
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
}
