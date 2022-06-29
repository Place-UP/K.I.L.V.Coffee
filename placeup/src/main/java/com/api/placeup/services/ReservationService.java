package com.api.placeup.services;

import com.api.placeup.domain.entities.Reservation;
import com.api.placeup.domain.enums.ReservationStatus;
import com.api.placeup.rest.dto.InformationReservationDTO;
import com.api.placeup.rest.dto.ReservationDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation save( ReservationDTO dto );
    Optional<Reservation> getCompleteReservation( Integer id);
    InformationReservationDTO getById(Integer id);
    void updateStatus(Integer id, ReservationStatus reservationStatus);
    List<InformationReservationDTO> getBySeller(Integer id);
    List<InformationReservationDTO> getByClient(Integer id);
}