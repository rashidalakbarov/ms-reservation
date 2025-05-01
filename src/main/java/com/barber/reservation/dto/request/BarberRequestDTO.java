package com.barber.reservation.dto.request;

import lombok.Data;

@Data
public class BarberRequestDTO {

    private String name;
    private String surname;
    private String phoneNumber;
    private String profileImageUrl;
    private String description;
}
