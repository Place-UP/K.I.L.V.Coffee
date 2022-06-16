package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
