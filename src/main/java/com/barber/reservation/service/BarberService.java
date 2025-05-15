package com.barber.reservation.service;

import com.barber.reservation.domain.Barber;
import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import com.barber.reservation.exception.ResourceNotFoundException;
import com.barber.reservation.mapper.BarberMapper;
import com.barber.reservation.repository.BarberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.barber.reservation.constant.MessageConstant.BARBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final BarberMapper barberMapper;

    public List<BarberResponseDTO> getAllBarbers() {
        return barberMapper.toBarberResponseDTOList(barberRepository.findAll());
    }

    public BarberResponseDTO getBarberById(Long id) {
        return barberMapper.toBarberResponseDTO(barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BARBER_NOT_FOUND.getMessage() + id)));

    }

    public BarberResponseDTO addBarber(BarberRequestDTO dto) {
        Barber entity = barberMapper.toBarber(dto);
        return barberMapper.toBarberResponseDTO(barberRepository.save(entity));
    }

    public BarberResponseDTO updateBarber(Long id, BarberRequestDTO dto) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BARBER_NOT_FOUND.getMessage() + id));
        barberMapper.updateBarberFromDTO(dto, barber);
        return barberMapper.toBarberResponseDTO(barberRepository.save(barber));
    }

    public void deleteBarber(Long id) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BARBER_NOT_FOUND.getMessage() + id));

        barberRepository.delete(barber);

    }
}