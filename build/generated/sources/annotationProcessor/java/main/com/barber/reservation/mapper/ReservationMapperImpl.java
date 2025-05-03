package com.barber.reservation.mapper;

import com.barber.reservation.domain.Reservation;
import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.ReservationRequestDTO;
import com.barber.reservation.dto.response.ReservationResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-03T21:07:03+0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation toEntity(ReservationRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setStartTime( dto.getStartTime() );
        reservation.setEndTime( dto.getEndTime() );
        reservation.setServiceName( dto.getServiceName() );
        reservation.setPrice( dto.getPrice() );

        return reservation;
    }

    @Override
    public ReservationResponseDTO toResponseDTO(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();

        reservationResponseDTO.setUserId( reservationUserId( reservation ) );
        reservationResponseDTO.setUsername( reservationUserUsername( reservation ) );
        reservationResponseDTO.setId( reservation.getId() );
        reservationResponseDTO.setStartTime( reservation.getStartTime() );
        reservationResponseDTO.setEndTime( reservation.getEndTime() );
        reservationResponseDTO.setServiceName( reservation.getServiceName() );
        reservationResponseDTO.setPrice( reservation.getPrice() );

        return reservationResponseDTO;
    }

    private Long reservationUserId(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        User user = reservation.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String reservationUserUsername(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        User user = reservation.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
