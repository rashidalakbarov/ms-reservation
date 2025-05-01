package com.barber.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //todo cutomer
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //todo barber
    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    @Index(name = "idx_barber_id")
    private Barber barber;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String serviceName;
    private double price;
}
