package com.barber.reservation.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDTO {

    private Long id;
    private Long userId;
    private String username;
    private Long barberId;
    private String barberName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String serviceName;
    private double price;
}
