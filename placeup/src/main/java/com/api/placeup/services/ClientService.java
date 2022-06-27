package com.api.placeup.services;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.rest.dto.ClientDTO;

public interface ClientService {
    public Client update(ClientDTO dto, Integer id);
    Client save(ClientDTO dto);
}
