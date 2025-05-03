package com.barber.reservation.repository;

import com.barber.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /**
     * More efficient query with join fetch to avoid the N+1 problem
     */
    @Query("SELECT r FROM Reservation r JOIN FETCH r.barber b JOIN FETCH r.user " +
       "WHERE CONCAT(b.name, ' ', b.surname) = :barberFullName")
    List<Reservation> findByBarberNameWithJoinFetch(@Param("barberFullName") String barberFullName);
}