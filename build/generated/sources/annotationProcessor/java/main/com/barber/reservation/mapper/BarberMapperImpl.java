package com.barber.reservation.mapper;

import com.barber.reservation.domain.Barber;
import com.barber.reservation.domain.Reservation;
import com.barber.reservation.dto.request.BarberRequestDTO;
import com.barber.reservation.dto.response.BarberResponseDTO;
import com.barber.reservation.dto.response.ReservationResponseDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-15T16:18:47+0400",
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
        barberResponseDTO.setName( barber.getName() );
        barberResponseDTO.setSurname( barber.getSurname() );
        barberResponseDTO.setPhoneNumber( barber.getPhoneNumber() );
        barberResponseDTO.setProfileImageUrl( barber.getProfileImageUrl() );
        barberResponseDTO.setDescription( barber.getDescription() );
        barberResponseDTO.setIsAvailable( barber.getIsAvailable() );
        barberResponseDTO.setReservations( reservationListToReservationResponseDTOList( barber.getReservations() ) );

        return barberResponseDTO;
    }

    @Override
    public Barber toBarber(BarberRequestDTO barberRequestDTO) {
        if ( barberRequestDTO == null ) {
            return null;
        }

        Barber barber = new Barber();

        barber.setName( barberRequestDTO.getName() );
        barber.setSurname( barberRequestDTO.getSurname() );
        barber.setProfileImageUrl( barberRequestDTO.getProfileImageUrl() );
        barber.setPhoneNumber( barberRequestDTO.getPhoneNumber() );
        barber.setDescription( barberRequestDTO.getDescription() );

        return barber;
    }

    @Override
    public void updateBarberFromDTO(BarberRequestDTO barberRequestDTO, Barber barber) {
        if ( barberRequestDTO == null ) {
            return;
        }

        barber.setName( barberRequestDTO.getName() );
        barber.setSurname( barberRequestDTO.getSurname() );
        barber.setProfileImageUrl( barberRequestDTO.getProfileImageUrl() );
        barber.setPhoneNumber( barberRequestDTO.getPhoneNumber() );
        barber.setDescription( barberRequestDTO.getDescription() );
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

    protected ReservationResponseDTO reservationToReservationResponseDTO(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();

        reservationResponseDTO.setId( reservation.getId() );
        reservationResponseDTO.setStartTime( reservation.getStartTime() );
        reservationResponseDTO.setEndTime( reservation.getEndTime() );
        reservationResponseDTO.setServiceName( reservation.getServiceName() );
        if ( reservation.getPrice() != null ) {
            reservationResponseDTO.setPrice( reservation.getPrice().doubleValue() );
        }

        return reservationResponseDTO;
    }

    protected List<ReservationResponseDTO> reservationListToReservationResponseDTOList(List<Reservation> list) {
        if ( list == null ) {
            return null;
        }

        List<ReservationResponseDTO> list1 = new ArrayList<ReservationResponseDTO>( list.size() );
        for ( Reservation reservation : list ) {
            list1.add( reservationToReservationResponseDTO( reservation ) );
        }

        return list1;
    }
}
