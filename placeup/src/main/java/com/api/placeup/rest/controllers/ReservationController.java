package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Reservation;
import com.api.placeup.domain.entities.ReservationItem;
import com.api.placeup.domain.enums.ReservationStatus;
import com.api.placeup.rest.dto.InformationItemReservationDTO;
import com.api.placeup.rest.dto.InformationReservationDTO;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.rest.dto.UpdateStatusReservationDTO;
import com.api.placeup.services.ReservationService;
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
@RequestMapping("/api/pedidos")
public class ReservationController {

    private ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid ReservationDTO dto ){
        Reservation reservation = service.save(dto);
        return reservation.getId();
    }

    @GetMapping("{id}")
    public InformationReservationDTO getById(@PathVariable Integer id ){
        return service
                .getCompleteReservation(id)
                .map( p -> convert(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Reservation not found."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id ,
                                @RequestBody UpdateStatusReservationDTO dto){
        String newStatus = dto.getNewStatus();
        service.updateStatus(id, ReservationStatus.valueOf(newStatus));
    }

    private InformationReservationDTO convert(Reservation reservation){
        return InformationReservationDTO
                .builder()
                .code(reservation.getId())
                .dateReservation(reservation.getReservationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .email(reservation.getClient().getEmail())
                .nameClient(reservation.getClient().getName())
                .total(reservation.getTotal())
                .status(reservation.getStatus().name())
                .nameSeller(reservation.getSeller().getName())
                .items(converter(reservation.getItems()))
                .build();
    }

    private List<InformationItemReservationDTO> converter(List<ReservationItem> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> InformationItemReservationDTO
                        .builder().productDescription(item.getProduct().getDescription())
                        .priceUnitary(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }

}