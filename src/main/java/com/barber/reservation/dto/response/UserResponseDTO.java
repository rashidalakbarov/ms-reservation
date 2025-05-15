package com.barber.reservation.dto.response;

import com.barber.reservation.domain.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String profileImageUrl;
    private String email;
    private String phoneNumber;
    private UserRole role;

}
