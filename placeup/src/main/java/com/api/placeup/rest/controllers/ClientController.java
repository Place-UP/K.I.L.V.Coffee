package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Clients;
import com.api.placeup.rest.dto.ClientDTO;
import com.api.placeup.security.jwt.JwtService;
import com.api.placeup.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Client getClientById(@PathVariable Integer id ){
        return service.getClientById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid ClientDTO dto ){
        Client client = service.save(dto);
        return client.getId();
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        service.delete(id);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid ClientDTO dto ){
        service.update(dto, id);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getClientFind( Client filter ) {
        return service.getClientFind(filter);
    }
}