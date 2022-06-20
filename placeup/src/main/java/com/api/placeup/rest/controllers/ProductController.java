package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Reservation;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.services.ProductService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService service;
    private Products repository;

    public ProductController(ProductService service, Products repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid ProductDTO dto ){
        Product product = service.save(dto);
        return product.getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid Product product ){
        repository
                .findById(id)
                .map( p -> {
                    product.setId(p.getId());
                    repository.save(product);
                    return product;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        repository
                .findById(id)
                .map( p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));
    }

    @GetMapping
    public ResponseEntity<Object> find( Product filter, @RequestParam("order") String order) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );


        Example<Product> example = Example.of(filter, matcher);
        List<Product> list = repository.findAll(example);

        Comparator<Product> compareByPrice = Comparator.comparing(Product::getPrice);

        if(Objects.equals(order, "ascending")) list.sort(compareByPrice);

        if(Objects.equals(order, "descending")) list.sort(compareByPrice.reversed());

        return ResponseEntity.ok(list);
    }
}