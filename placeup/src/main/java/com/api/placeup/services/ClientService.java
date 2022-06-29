package com.api.placeup.services;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.rest.dto.ClientDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client update(ClientDTO dto, Integer id);
    Client getClientById(@PathVariable Integer id );
    Client save(ClientDTO dto);
    List<Client> getClientFind(Client filter );
    void delete(@PathVariable Integer id);

}
