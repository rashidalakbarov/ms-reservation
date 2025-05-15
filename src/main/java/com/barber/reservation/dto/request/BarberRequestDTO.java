package com.barber.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BarberRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @Size(max = 255, message = "Phone number is too long")
    private String phoneNumber;

    private String profileImageUrl;
    private String description;
}

