package com.barber.reservation.mapper;


import com.barber.reservation.domain.User;
import com.barber.reservation.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // User to UserDto
    UserDto toUserDto(User user);

    // UserDto to User
    User toUser(UserDto userDto);


}
