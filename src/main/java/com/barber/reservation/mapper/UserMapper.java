package com.barber.reservation.mapper;

import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO toUserResponseDTO(User user);

    User toEntity(UserRequestDTO dto);

}
