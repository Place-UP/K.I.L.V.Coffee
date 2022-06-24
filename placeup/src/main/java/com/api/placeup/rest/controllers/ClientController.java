package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.repositories.Clients;
import com.api.placeup.rest.dto.ClientDTO;
import com.api.placeup.security.jwt.JwtService;
import com.api.placeup.services.ClientService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private Clients clients;
    private ClientService service;

    public ClientController( Clients clients, ClientService service) {
        this.service = service;
        this.clients = clients;
    }

    @GetMapping("{id}")
    public Client getClientById(@PathVariable Integer id ){
        return clients
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Client not found."));
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
        clients.findById(id)
                .map( client -> {
                    clients.delete(client );
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found.") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody @Valid Client cliente ){
        clients
                .findById(id)
                .map( clientExistent -> {
                    cliente.setId(clientExistent.getId());
                    clients.save(cliente);
                    return clientExistent;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found") );
    }

    @GetMapping
    public List<Client> find(Client filter ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filter, matcher);
        return clients.findAll(example);
    }

}