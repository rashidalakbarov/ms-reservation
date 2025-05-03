package com.barber.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "barbers")
@Getter
@Setter
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String profileImageUrl;
    private String phoneNumber;
    private String description;
    private Boolean isAvailable;

    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}