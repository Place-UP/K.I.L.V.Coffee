package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ProductUpdateDTO;
import com.api.placeup.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final Products productRepository;
    private final Sellers sellerRepository;

    @Override
    @Transactional
    public Product save(ProductDTO dto){
        Integer sellerId = dto.getSeller();
        Seller seller = sellerRepository
                .findById(sellerId)
                .orElseThrow(() -> new BusinessRuleException("Invalid seller code."));

        Product product = new Product();
        product.setSeller(seller);
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        productRepository.save(product);
        return product;
    }

    @Override
    @Transactional
    public Product update(ProductUpdateDTO dto, Integer id){

        Product product = productRepository.findById(id)
                .map( existentProduct -> {
                    dto.setId(existentProduct.getId());
                    dto.setSeller(existentProduct.getSeller());
                    return existentProduct;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));

        product.setSeller(dto.getSeller());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        productRepository.save(product);
        return product;
    }

}
