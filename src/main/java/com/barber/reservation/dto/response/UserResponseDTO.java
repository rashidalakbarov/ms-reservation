package com.barber.reservation.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String profileImageUrl;
    private String email;
    private String phoneNumber;
}
