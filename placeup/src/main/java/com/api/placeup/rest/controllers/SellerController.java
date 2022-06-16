package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Sellers;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/lojas")
public class SellerController {

    private Sellers sellers;

    public SellerController( Sellers sellers ) {
        this.sellers = sellers;
    }

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
    public Seller save( @RequestBody Seller seller ){
        return sellers.save(seller);
    }

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