package com.api.placeup.services;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ProductUpdateDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    Product update(ProductUpdateDTO dto, Integer id);
    Product save(ProductDTO dto);
    Product getById(Integer id);
    List<Product> find(Product filter, String order);
    List<Product> getBySeller(Integer id);
    void delete(Integer id);
}
