package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Reservation;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ProductUpdateDTO;
import com.api.placeup.rest.dto.ReservationDTO;
import com.api.placeup.services.ProductService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid ProductDTO dto ){
        return service.save(dto).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid ProductUpdateDTO dto ){
        service.update(dto, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> find( Product filter, @RequestParam("order") String order) {
        return service.find(filter, order);
    }
}