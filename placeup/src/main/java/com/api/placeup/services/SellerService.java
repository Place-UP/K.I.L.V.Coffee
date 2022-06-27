package com.api.placeup.services;

import com.api.placeup.domain.entities.Seller;
import com.api.placeup.rest.dto.SellerDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Seller save (SellerDTO dto);
    Seller update (SellerDTO dto, Integer id);
    Optional<Seller> getCompleteSeller(Integer id);
    Seller getSellerById(@PathVariable Integer id );
    List<Seller> find(Seller filter );
    void delete( @PathVariable Integer id );

}
