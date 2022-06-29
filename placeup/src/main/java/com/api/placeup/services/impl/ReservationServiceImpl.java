package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.*;
import com.api.placeup.domain.enums.ReservationStatus;
import com.api.placeup.domain.repositories.*;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.exceptions.ReservationNotFoundException;
import com.api.placeup.rest.controllers.ClientController;
import com.api.placeup.rest.controllers.ReservationController;
import com.api.placeup.rest.controllers.SellerController;
import com.api.placeup.rest.dto.InformationItemReservationDTO;
import com.api.placeup.rest.dto.InformationReservationDTO;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.rest.dto.ReservationItemDTO;
import com.api.placeup.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public InformationReservationDTO getById(Integer id) {
        InformationReservationDTO reservation = getCompleteReservation(id)
                    .map( p -> convert(p) )
                    .orElseThrow(() ->
                            new ResponseStatusException(NOT_FOUND, "Reservation not found."));

        reservation.add(linkTo(methodOn(SellerController.class).getSellerById(id)).withRel("Seller link"));
        reservation.add(linkTo(methodOn(ClientController.class).getClientById(id)).withRel("Client link"));

        return reservation;
    }



    @Override
    public List<InformationReservationDTO> getBySeller(Integer id) {
        List<InformationReservationDTO> list = new ArrayList<>();
        for (Reservation reservation : repository.findBySeller(id)) {
            InformationReservationDTO reservationDTO = getCompleteReservation(reservation.getId())
                    .map(p -> convert(p))
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Reservation not found."));

            reservationDTO.add(linkTo(methodOn(ReservationController.class).getById(reservationDTO.getCode())).withSelfRel());
            reservationDTO.add(linkTo(methodOn(SellerController.class).getSellerById(id)).withRel("Seller link"));
            reservationDTO.add(linkTo(methodOn(ClientController.class).getClientById(id)).withRel("Client link"));

            list.add(reservationDTO);
        }

        return list;
    }



    @Override
    public List<InformationReservationDTO> getByClient(Integer id) {
        List<Reservation> reservationsList = repository.findByClient(id);

        List<InformationReservationDTO> list = new ArrayList<>();
        for (Reservation reservation : repository.findByClient(id)) {
            InformationReservationDTO reservationDTO = getCompleteReservation(reservation.getId())
                    .map(p -> convert(p))
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Reservation not found."));

            reservationDTO.add(linkTo(methodOn(ReservationController.class).getById(reservationDTO.getCode())).withSelfRel());
            reservationDTO.add(linkTo(methodOn(SellerController.class).getSellerById(id)).withRel("Seller link"));
            reservationDTO.add(linkTo(methodOn(ClientController.class).getClientById(id)).withRel("Client link"));

            list.add(reservationDTO);
        }

        return list;
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
    private InformationReservationDTO convert(Reservation reservation){
        return InformationReservationDTO
                .builder()
                .code(reservation.getId())
                .dateReservation(reservation.getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withdrawalDate(reservation.getWithdrawalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
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