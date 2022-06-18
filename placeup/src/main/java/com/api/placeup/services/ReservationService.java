package com.api.placeup.services;

import com.api.placeup.domain.entities.Reservation;
import com.api.placeup.domain.enums.ReservationStatus;
import com.api.placeup.rest.dto.ReservationDTO;

import java.util.Optional;

public interface ReservationService {
    Reservation save( ReservationDTO dto );
    Optional<Reservation> getCompleteReservation(Integer id);
    void updateStatus(Integer id, ReservationStatus statusPedido);
}