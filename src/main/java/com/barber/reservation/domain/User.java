package com.barber.reservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_phone", columnList = "phoneNumber")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String profileImageUrl;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)[0-9]{7}$")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;
}
