package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.entities.User;
import com.api.placeup.domain.enums.UserType;
import com.api.placeup.domain.repositories.Clients;
import com.api.placeup.domain.repositories.UserRepository;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.controllers.ReservationController;
import com.api.placeup.rest.controllers.SellerController;
import com.api.placeup.rest.dto.ClientDTO;
import com.api.placeup.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final Clients repository;
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @Override
    @Transactional
    public Client save(ClientDTO dto) {

        if(nameAlreadyExists(dto)) {
            throw new BusinessRuleException("This name already is in use.");
        }
        if(emailAlreadyExixsts(dto)) {
            throw new BusinessRuleException("This email already is in use");
        }

        Client client = new Client();
        User user = new User();

        user.setLogin(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserType(UserType.CLIENT);

        client.setEmail(dto.getEmail());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        client.setUser(user);

        repository.save(client);
        userService.save(user);

        return client;
    }

    @Override
    @Transactional
    public Client update(ClientDTO dto, Integer id) {

        Client client = repository.findById(id)
                .map( clientExistent -> {
                    dto.setClient(clientExistent.getId());
                    dto.setEmail(clientExistent.getEmail());
                    dto.setUser(clientExistent.getUser());
                    return clientExistent;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found") );

        User user = dto.getUser();

        client.setEmail(dto.getEmail());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        client.setUser(user);

        repository.save(client);
        userService.save(user);

        return client;
    }

    @Override
    public Client getClientById(@PathVariable Integer id ){
        Client client = repository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Client not found."));

        client.add(linkTo(methodOn(ReservationController.class).getByClient(id)).withRel("Reservations link"));

        return client;
    }

    @Override
    public List<Client> getClientFind(Client filter ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );

        Example<Client> example = Example.of(filter, matcher);
        List<Client> list = repository.findAll(example);

        for(Client client : list) {
            client.add(linkTo(methodOn(ReservationController.class)
                    .getByClient(client.getId())).withRel("Reservations link"));
        }

        return list;
    }

    @Override
    public void delete( @PathVariable Integer id ){
        repository.findById(id)
                .map( client -> {
                    repository.delete(client );
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found.") );
    }

    private boolean nameAlreadyExists(ClientDTO dto) {
        Optional<Client> client = repository.findByName(dto.getName());
        return client.isPresent();
    }

    private boolean emailAlreadyExixsts(ClientDTO dto) {
        Optional<Client> client = repository.findByEmail(dto.getEmail());
        return client.isPresent();
    }

}
