package com.api.placeup.services;

import com.api.placeup.domain.entities.Seller;
import com.api.placeup.rest.dto.SellerDTO;

import java.util.Optional;

public interface SellerService {
    Seller save (SellerDTO dto);
    Optional<Seller> getCompleteSeller(Integer id);
}
