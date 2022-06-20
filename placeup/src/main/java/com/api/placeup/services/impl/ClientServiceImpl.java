package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.repositories.Clients;
import com.api.placeup.rest.dto.ClientDTO;
import com.api.placeup.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final Clients repository;

    @Override
    @Transactional
    public Client save(ClientDTO dto) {
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setName(dto.getName());
        client.setPassword(dto.getPassword());
        repository.save(client);
        return client;
    }

}
