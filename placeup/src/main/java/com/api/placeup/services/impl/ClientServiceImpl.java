package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.User;
import com.api.placeup.domain.enums.UserType;
import com.api.placeup.domain.repositories.Clients;
import com.api.placeup.domain.repositories.UserRespository;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.dto.ClientDTO;
import com.api.placeup.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final Clients repository;
    private final UserRespository usersRepository;
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
        client.setUser(user);

        repository.save(client);
        userService.save(user);

        return client;
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
