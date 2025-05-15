package com.barber.reservation.service;

import com.barber.reservation.domain.Barber;
import com.barber.reservation.domain.Reservation;
import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.ReservationRequestDTO;
import com.barber.reservation.dto.request.UpdateReservationRequestDTO;
import com.barber.reservation.dto.response.ReservationResponseDTO;
import com.barber.reservation.exception.ResourceNotFoundException;
import com.barber.reservation.mapper.ReservationMapper;
import com.barber.reservation.repository.BarberRepository;
import com.barber.reservation.repository.ReservationRepository;
import com.barber.reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.barber.reservation.constant.MessageConstant.BARBER_ALREADY_BOOKED;
import static com.barber.reservation.constant.MessageConstant.BARBER_NOT_FOUND;
import static com.barber.reservation.constant.MessageConstant.RESERVATION_NOT_FOUND;
import static com.barber.reservation.constant.MessageConstant.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BarberRepository barberRepository;
    private final ReservationMapper reservationMapper;

    @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO dto) throws BadRequestException {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage() + dto.getUserId()));

        Barber barber = barberRepository.findById(dto.getBarberId())
                .orElseThrow(() -> new ResourceNotFoundException(BARBER_NOT_FOUND.getMessage() + dto.getBarberId()));

        boolean isOverlappingReservation = reservationRepository.isOverlappingReservation(
                dto.getBarberId(), dto.getEndTime(), dto.getStartTime()
        );
        if (isOverlappingReservation) {
            throw new BadRequestException(BARBER_ALREADY_BOOKED.getMessage());
        }

        Reservation reservation = reservationMapper.toEntity(dto);
        reservation.setUser(user);
        reservation.setBarber(barber);

        return reservationMapper.toResponseDTO(reservationRepository.save(reservation));
    }

    public List<ReservationResponseDTO> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponseDTO)
                .toList();
    }

    public List<ReservationResponseDTO> getReservationsByBarberName(String barberName) {
        return reservationRepository.findReservationsByBarberFullName(barberName)
                .stream()
                .map(reservationMapper::toResponseDTO)
                .toList();
    }


    public void delete(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESERVATION_NOT_FOUND.getMessage() + id);
        }
        reservationRepository.deleteById(id);
    }

    @Transactional
    public ReservationResponseDTO update(UpdateReservationRequestDTO dto) {
        Reservation existing = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION_NOT_FOUND.getMessage() + dto.getReservationId()));

        // Update fields
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setServiceName(dto.getServiceName());
        existing.setPrice(dto.getPrice());

        // Optionally check user/barber existence if needed
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage() + dto.getUserId()));
        existing.setUser(user);

        Barber barber = barberRepository.findById(dto.getBarberId())
                .orElseThrow(() -> new ResourceNotFoundException(BARBER_NOT_FOUND.getMessage() + dto.getBarberId()));
        existing.setBarber(barber);

        Reservation updated = reservationRepository.save(existing);
        return reservationMapper.toResponseDTO(updated);
    }
}