package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.repositories.Clients;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    private Clients clients;

    public ClientController( Clients clients ) {
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
    public Client save( @RequestBody Client client ){
        return clients.save(client);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        clients.findById(id)
                .map( client -> {
                    clients.delete(client);
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody Client client ){
        clients
                .findById(id)
                .map( clientExistente -> {
                    client.setId(clientExistente.getId());
                    clients.save(client);
                    return clientExistente;
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