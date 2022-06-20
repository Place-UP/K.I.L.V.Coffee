package com.api.placeup.services;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.rest.dto.ClientDTO;

public interface ClientService {
    Client save(ClientDTO dto);
}
