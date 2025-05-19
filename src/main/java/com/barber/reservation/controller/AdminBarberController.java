package com.barber.reservation.controller;

import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import com.barber.reservation.service.BarberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/barbers")
public class AdminBarberController {

    private final BarberService barberService;

    @GetMapping
    public List<BarberResponseDTO> getAllBarbers() {
        return barberService.getAllBarbers();
    }

    @GetMapping("/{id}")
    public BarberResponseDTO getBarberById(@PathVariable Long id) {
        return barberService.getBarberById(id);
    }

    @PostMapping
    public BarberResponseDTO addBarber(@Valid @RequestBody BarberRequestDTO barberRequestDTO) {
        return barberService.addBarber(barberRequestDTO);
    }

    @PutMapping("/{id}")
    public BarberResponseDTO updateBarber(@PathVariable Long id,
                                          @Valid @RequestBody BarberRequestDTO barberRequestDTO) {
        return barberService.updateBarber(id, barberRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
    }
}