package com.barber.reservation.dto.response;

import lombok.Data;

@Data
public class BarberResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String profileImageUrl;
    private String description;
    private Boolean isAvailable;
}