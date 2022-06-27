package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Reservation;
import com.api.placeup.domain.entities.ReservationItem;
import com.api.placeup.domain.enums.ReservationStatus;
import com.api.placeup.rest.dto.InformationItemReservationDTO;
import com.api.placeup.rest.dto.InformationReservationDTO;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.rest.dto.UpdateStatusReservationDTO;
import com.api.placeup.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid ReservationDTO dto ){
        Reservation reservation = service.save(dto);
        return reservation.getId();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public InformationReservationDTO getById(@PathVariable Integer id ){
        return getById(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id , @RequestBody UpdateStatusReservationDTO dto){
        service.updateStatus(id, ReservationStatus.valueOf(dto.getNewStatus()));
    }

}