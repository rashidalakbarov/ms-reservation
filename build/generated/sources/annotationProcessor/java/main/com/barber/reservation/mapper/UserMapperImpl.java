package com.barber.reservation.mapper;

import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T09:45:43+0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDTO toUserResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( user.getId() );
        userResponseDTO.setUsername( user.getUsername() );
        userResponseDTO.setProfileImageUrl( user.getProfileImageUrl() );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setPhoneNumber( user.getPhoneNumber() );

        return userResponseDTO;
    }

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setPhoneNumber( dto.getPhoneNumber() );
        user.setPassword( dto.getPassword() );

        return user;
    }
}
