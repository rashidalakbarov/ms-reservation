package com.barber.reservation.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String phoneNumber;
    private String password;
}

