package com.barber.reservation.mapper;

import com.barber.reservation.domain.Barber;
import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-01T16:38:05+0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class BarberMapperImpl implements BarberMapper {

    @Override
    public BarberResponseDTO toBarberResponseDTO(Barber barber) {
        if ( barber == null ) {
            return null;
        }

        BarberResponseDTO barberResponseDTO = new BarberResponseDTO();

        barberResponseDTO.setId( barber.getId() );
        barberResponseDTO.setPhoneNumber( barber.getPhoneNumber() );
        barberResponseDTO.setProfileImageUrl( barber.getProfileImageUrl() );
        barberResponseDTO.setDescription( barber.getDescription() );
        barberResponseDTO.setIsAvailable( barber.getIsAvailable() );

        barberResponseDTO.setName( barber.getFullName() != null && barber.getFullName().contains(" ") ? barber.getFullName().split(" ", 2)[0] : barber.getFullName() );
        barberResponseDTO.setSurname( barber.getFullName() != null && barber.getFullName().contains(" ") ? barber.getFullName().split(" ", 2)[1] : "" );

        return barberResponseDTO;
    }

    @Override
    public Barber toBarber(BarberRequestDTO barberRequestDTO) {
        if ( barberRequestDTO == null ) {
            return null;
        }

        Barber barber = new Barber();

        barber.setProfileImageUrl( barberRequestDTO.getProfileImageUrl() );
        barber.setPhoneNumber( barberRequestDTO.getPhoneNumber() );
        barber.setDescription( barberRequestDTO.getDescription() );

        barber.setFullName( barberRequestDTO.getName() + ' ' + barberRequestDTO.getSurname() );

        return barber;
    }

    @Override
    public void updateBarberFromDTO(BarberRequestDTO barberRequestDTO, Barber barber) {
        if ( barberRequestDTO == null ) {
            return;
        }

        barber.setProfileImageUrl( barberRequestDTO.getProfileImageUrl() );
        barber.setPhoneNumber( barberRequestDTO.getPhoneNumber() );
        barber.setDescription( barberRequestDTO.getDescription() );

        barber.setFullName( barberRequestDTO.getName() + ' ' + barberRequestDTO.getSurname() );
    }

    @Override
    public List<BarberResponseDTO> toBarberResponseDTOList(List<Barber> barbers) {
        if ( barbers == null ) {
            return null;
        }

        List<BarberResponseDTO> list = new ArrayList<BarberResponseDTO>( barbers.size() );
        for ( Barber barber : barbers ) {
            list.add( toBarberResponseDTO( barber ) );
        }

        return list;
    }
}
