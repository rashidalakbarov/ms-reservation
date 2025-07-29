package com.barber.reservation.domain;

import com.barber.reservation.domain.enums.UserRole;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CUSTOMER;

//    private String imageName;
//    private String imageType;
// @Lob
//    private byte[] imageData;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)[0-9]{7}$")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

}