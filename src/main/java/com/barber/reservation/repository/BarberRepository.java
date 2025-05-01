package com.barber.reservation.repository;

import com.barber.reservation.domain.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
}
