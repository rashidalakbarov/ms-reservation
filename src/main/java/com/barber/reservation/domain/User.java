package com.barber.reservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(\\+994|0)(50|51|55|60|70|77|99)\\d{7}$")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isBarber = false;
}
