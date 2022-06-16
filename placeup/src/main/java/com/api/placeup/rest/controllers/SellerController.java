package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Address;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.ReservationItem;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Addresses;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.dto.InformationItemReservationDTO;
import com.api.placeup.rest.dto.SellerDTO;
import com.api.placeup.services.SellerService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/lojas")
public class SellerController {

    private Sellers sellers;

    private Addresses addresses;
    private SellerService service;

    public SellerController( Sellers sellers, SellerService service ) {
        this.sellers = sellers;
        this.service = service;
    }

//    @GetMapping("{id}")
//    public Seller getById(@PathVariable Integer id ){
//        return sellers.findById(id);
//
//        return service
//                .getCompleteSeller(id)
//                .map( s -> convert (s) )
//                .orElseThrow(() ->
//                      new ResponseStatusException(NOT_FOUND, "Reservation not found."));
//    }

    @GetMapping("{id}")
    public Seller getSellerById(@PathVariable Integer id ){
        return sellers
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Seller not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody SellerDTO dto){
        Seller seller = service.save(dto);
        return seller.getId();
    }

//    private SellerDTO convert(Seller seller){
//        return SellerDTO
//                .builder()
//                .seller(seller.getId())
//                .name(seller.getName())
//                .email(seller.getEmail())
//                .cnpj(seller.getCnpj())
//                .phone(seller.getPhone())
//                .password(seller.getPassword())
//
//                .address(converter(seller.getAddress().getId()))
//
//                .build();
//    }

//    private SellerDTO converter(Integer addressId){
//        //Integer addressId = address.getId();
//
//        Address address = addresses
//                .findById(addressId)
//                .orElseThrow(
//                        () -> new BusinessRuleException(
//                                "Invalid product code: "+ addressId
//                        ));
//        return SellerDTO
//                .builder().address(address.getId())
//                .state(address.getState())
//                .city(address.getCity())
//                .district(address.getDistrict())
//                .street(address.getStreet())
//                .houseNumber(address.getHouseNumber())
//                .build();
//    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        sellers.findById(id)
                .map( seller -> {
                    sellers.delete(seller );
                    return seller;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Seller not found.") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody Seller seller){
        sellers
                .findById(id)
                .map( sellerExistent -> {
                    seller.setId(sellerExistent.getId());
                    sellers.save(seller);
                    return sellerExistent;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Seller not found") );
    }

    @GetMapping
    public ResponseEntity<Object> find( Seller filter ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );

        Example<Seller> example = Example.of(filter, matcher);
        List<Seller> list = sellers.findAll(example);

        return ResponseEntity.ok(list);
    }
}