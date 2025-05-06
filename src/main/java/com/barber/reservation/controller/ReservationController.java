package com.barber.reservation.controller;

import com.barber.reservation.dto.request.ReservationRequestDTO;
import com.barber.reservation.dto.request.UpdateReservationRequestDTO;
import com.barber.reservation.dto.response.ReservationResponseDTO;
import com.barber.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponseDTO create(@Valid @RequestBody ReservationRequestDTO dto) {
        return reservationService.create(dto);
    }

    @GetMapping("/{barberName}")
    public List<ReservationResponseDTO> getReservationsByBarberName(@PathVariable String barberName) {
        return reservationService.getReservationsByBarberName(barberName);
    }

    @GetMapping
    public List<ReservationResponseDTO> getAll() {
        return reservationService.getAll();
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
    }

    @PutMapping("/edit")
    public ReservationResponseDTO update(@Valid @RequestBody UpdateReservationRequestDTO dto) {
        return reservationService.update(dto);
    }
}
