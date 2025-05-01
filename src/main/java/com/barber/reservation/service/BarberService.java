package com.barber.reservation.service;

import com.barber.reservation.domain.Barber;
import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import com.barber.reservation.exception.ResourceNotFoundException;
import com.barber.reservation.mapper.BarberMapper;
import com.barber.reservation.repository.BarberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final BarberMapper barberMapper;

    @Transactional(readOnly = true)
    public List<BarberResponseDTO> getAllBarbers() {
        return barberMapper.toBarberResponseDTOList(barberRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BarberResponseDTO getBarberById(Long id) {
        return barberMapper.toBarberResponseDTO(barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found with id: " + id)));

    }

    @Transactional
    public BarberResponseDTO addBarber(BarberRequestDTO dto) {
        Barber entity = barberMapper.toBarber(dto);
        return barberMapper.toBarberResponseDTO(barberRepository.save(entity));
    }

    @Transactional
    public BarberResponseDTO updateBarber(Long id, BarberRequestDTO dto) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found with id: " + id));
        barberMapper.updateBarberFromDTO(dto, barber);
        return barberMapper.toBarberResponseDTO(barberRepository.save(barber));
    }

    @Transactional
    public void deleteBarber(Long id) {
        barberRepository.deleteById(id);
    }
}