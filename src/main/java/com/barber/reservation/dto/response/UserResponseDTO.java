package com.barber.reservation.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean isBarber;
}
