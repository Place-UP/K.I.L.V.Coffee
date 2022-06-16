package com.api.placeup.services;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.rest.dto.ProductDTO;

public interface ProductService {
    Product save(ProductDTO dto);
}
