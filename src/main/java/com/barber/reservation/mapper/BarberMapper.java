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

    // Map fullName to name and surname
    @Mapping(target = "name", expression = "java(barber.getFullName() != null && barber.getFullName().contains(\" \") ? barber.getFullName().split(\" \", 2)[0] : barber.getFullName())")
    @Mapping(target = "surname", expression = "java(barber.getFullName() != null && barber.getFullName().contains(\" \") ? barber.getFullName().split(\" \", 2)[1] : \"\")")
    BarberResponseDTO toBarberResponseDTO(Barber barber);

    // Map fields when creating a new barber, no mapping for isAvailable (unless you add it to DTO)
    @Mapping(target = "fullName", expression = "java(barberRequestDTO.getName() + ' ' + barberRequestDTO.getSurname())")
    @Mapping(target = "profileImageUrl", source = "profileImageUrl")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Barber toBarber(BarberRequestDTO barberRequestDTO);

    @Mapping(target = "fullName", expression = "java(barberRequestDTO.getName() + ' ' + barberRequestDTO.getSurname())")
    @Mapping(target = "profileImageUrl", source = "profileImageUrl")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateBarberFromDTO(BarberRequestDTO barberRequestDTO, @MappingTarget Barber barber);

    List<BarberResponseDTO> toBarberResponseDTOList(List<Barber> barbers);
}