package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.*;
import com.api.placeup.domain.enums.StatusPedido;
import com.api.placeup.domain.repositories.Clients;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.ReservationItems;
import com.api.placeup.domain.repositories.Reservations;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.exceptions.ReservationNotFoundException;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.rest.dto.ReservationItemDTO;
import com.api.placeup.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final Reservations repository;
    private final Clients clientsRepository;
    private final Products productRepository;
    private final ReservationItems reservationItems;


    @Override
    @Transactional
    public Reservation save(ReservationDTO dto ) {
        Integer clientId = dto.getClient();
        Client client = clientsRepository
                .findById(clientId)
                .orElseThrow(() -> new BusinessRuleException("Invalid client code."));

        Reservation reservation = new Reservation();
        reservation.setTotal(dto.getTotal());
        reservation.setReservationDate(LocalDate.now());
        reservation.setClient(client);
        reservation.setStatus(StatusPedido.PENDENTE);

        List<ReservationItem> reservationItem = convertItems(reservation, dto.getItems());
        repository.save(reservation);
        reservationItems.saveAll(reservationItem);
        reservation.setItems(reservationItem);
        return reservation;
    }

    @Override
    public Optional<Reservation> getCompleteReservation(Integer id) {
        return repository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void updateStatus( Integer id, StatusPedido reservationStatus ) {
        repository
                .findById(id)
                .map( reservation -> {
                    reservation.setStatus(reservationStatus);
                    return repository.save(reservation);
                }).orElseThrow(() -> new ReservationNotFoundException() );
    }

    private List<ReservationItem> convertItems(Reservation reservation, List<ReservationItemDTO> items){
        if(items.isEmpty()){
            throw new BusinessRuleException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer productId = dto.getProduct();
                    Product product = productRepository
                            .findById(productId)
                            .orElseThrow(
                                    () -> new BusinessRuleException(
                                            "Invalid product code: "+ productId
                                    ));

                    ReservationItem reservationItem = new ReservationItem();
                    reservationItem.setQuantity(dto.getQuantity());
                    reservationItem.setReservation(reservation);
                    reservationItem.setProduct(product);
                    return reservationItem;
                }).collect(Collectors.toList());
    }
}