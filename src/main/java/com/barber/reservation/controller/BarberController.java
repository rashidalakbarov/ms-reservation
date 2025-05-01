package com.barber.reservation.controller;

import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import com.barber.reservation.service.BarberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/barber")
public class BarberController {

    private final BarberService barberService;

    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> getAllBarbers() {
        return ResponseEntity.ok(barberService.getAllBarbers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> getBarberById(@PathVariable Long id) {
        return ResponseEntity.ok(barberService.getBarberById(id));
    }

    @PostMapping
    public ResponseEntity<BarberResponseDTO> addBarber(@RequestBody BarberRequestDTO barberRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(barberService.addBarber(barberRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> updateBarber(@PathVariable Long id,
                                                          @RequestBody BarberRequestDTO barberRequestDTO) {
        return ResponseEntity.ok(barberService.updateBarber(id, barberRequestDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
    }
}