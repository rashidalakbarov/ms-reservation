package com.barber.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;
}
