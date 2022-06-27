package com.api.placeup.services;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ProductUpdateDTO;

public interface ProductService {
    Product update(ProductUpdateDTO dto, Integer id);
    Product save(ProductDTO dto);
}
