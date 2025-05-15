package com.barber.reservation.repository;

import com.barber.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /**
     * More efficient query with join fetch to avoid the N+1 problem
     */
    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.barber b " +
            "JOIN FETCH r.user u " +
            "WHERE LOWER(CONCAT(b.name, ' ', b.surname)) = LOWER(:barberFullName)")
    List<Reservation> findReservationsByBarberFullName(@Param("barberFullName") String barberFullName);

    boolean isOverlappingReservation(
            Long barberId, LocalDateTime endTime, LocalDateTime startTime
    );

}