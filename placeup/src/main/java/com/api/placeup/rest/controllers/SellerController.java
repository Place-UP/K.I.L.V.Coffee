package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Addresses;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.rest.dto.SellerDTO;
import com.api.placeup.security.jwt.JwtService;
import com.api.placeup.services.SellerService;
import com.api.placeup.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService service;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Seller getSellerById(@PathVariable Integer id ){
        return service.getSellerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid SellerDTO dto) {
        return service.save(dto).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        service.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid SellerDTO dto){
        service.update(dto, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Seller> find( Seller filter ) {
        return service.find(filter);
    }
}