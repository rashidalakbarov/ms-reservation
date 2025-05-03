package com.barber.reservation.mapper;

import com.barber.reservation.domain.Barber;
import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BarberMapper {

    BarberMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BarberMapper.class);

    BarberResponseDTO toBarberResponseDTO(Barber barber);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "profileImageUrl", source = "profileImageUrl")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Barber toBarber(BarberRequestDTO barberRequestDTO);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "profileImageUrl", source = "profileImageUrl")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateBarberFromDTO(BarberRequestDTO barberRequestDTO, @MappingTarget Barber barber);

    List<BarberResponseDTO> toBarberResponseDTOList(List<Barber> barbers);
}