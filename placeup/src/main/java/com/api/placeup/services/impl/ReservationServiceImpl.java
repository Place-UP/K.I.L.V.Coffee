package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.*;
import com.api.placeup.domain.enums.ReservationStatus;
import com.api.placeup.domain.repositories.*;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.exceptions.ReservationNotFoundException;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.rest.dto.ReservationItemDTO;
import com.api.placeup.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final Reservations repository;
    private final Clients clientsRepository;
    private final Sellers sellersRepository;
    private final Products productRepository;
    private final ReservationItems reservationItems;


    @Override
    @Transactional
    public Reservation save(ReservationDTO dto ) {
        Integer clientId = dto.getClient();
        Client client = clientsRepository
                .findById(clientId)
                .orElseThrow(() -> new BusinessRuleException("Invalid client code."));

        Integer sellerId = dto.getSeller();
        Seller seller = sellersRepository
                .findById(sellerId)
                .orElseThrow(() -> new BusinessRuleException("Invalid seller code."));

        List<BigDecimal> prices = getProductPrice(dto.getItems());
        BigDecimal total = totalCalculation(prices);

        Reservation reservation = new Reservation();
        reservation.setTotal(total);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setWithdrawalDate(dto.getWithdrawalDate());
        reservation.setClient(client);
        reservation.setSeller(seller);
        reservation.setStatus(ReservationStatus.PENDING);

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
    public void updateStatus( Integer id, ReservationStatus reservationStatus ) {
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

    private List<BigDecimal> getProductPrice(List<ReservationItemDTO> items) {
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

                    BigDecimal quantity = new BigDecimal(dto.getQuantity());
                    return product.getPrice().multiply(quantity);
                }).collect(Collectors.toList());
    }

    private BigDecimal totalCalculation(List<BigDecimal> prices) {
        BigDecimal total = BigDecimal.valueOf(0.00);
        for(BigDecimal price : prices) {
            total = total.add(price);
        }
        return total;
    }

}