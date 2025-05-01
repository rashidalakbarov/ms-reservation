package com.barber.reservation.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {

    private Long userId;
    private Long barberId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String serviceName;
    private double price;
}
