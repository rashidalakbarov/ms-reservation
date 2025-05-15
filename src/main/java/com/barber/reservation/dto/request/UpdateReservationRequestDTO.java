package com.barber.reservation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateReservationRequestDTO {

    private Long reservationId;
    private Long userId;
    private Long barberId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String serviceName;
    private BigDecimal price;
}
