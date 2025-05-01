package com.barber.reservation.mapper;

import com.barber.reservation.domain.Reservation;
import com.barber.reservation.dto.request.ReservationRequestDTO;
import com.barber.reservation.dto.response.ReservationResponseDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    Reservation toEntity(ReservationRequestDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "barber.id", target = "barberId")
    @Mapping(source = "barber.fullName", target = "barberName")
    ReservationResponseDTO toResponseDTO(Reservation reservation);
}
