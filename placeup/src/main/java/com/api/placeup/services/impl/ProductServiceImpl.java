package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.controllers.SellerController;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ProductUpdateDTO;
import com.api.placeup.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.NO_CONTENT;

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
    @Override
    public void delete(Integer id){
        productRepository
                .findById(id)
                .map( p -> {
                    productRepository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));
    }
    @Override
    public Product getById(Integer id){
        return productRepository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));
    }
    @Override
    public List<Product> getBySeller(Integer id) {
        List<Product> productList = productRepository.findBySeller(id);
        if (productList.isEmpty()){
            return null;
        } else{
            for (Product product: productList){
                product.add(linkTo(methodOn(SellerController.class).getSellerById(id)).withRel("Seller link"));
            }
        }

        return productList;
    }



    @Override
    public List<Product> find(Product filter, String order) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );


        Example<Product> example = Example.of(filter, matcher);
        List<Product> list = productRepository.findAll(example);

        Comparator<Product> compareByPrice = Comparator.comparing(Product::getPrice);

        if(Objects.equals(order, "ascending")) list.sort(compareByPrice);

        if(Objects.equals(order, "descending")) list.sort(compareByPrice.reversed());

        return list;
    }

}
