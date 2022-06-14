package com.api.placeup.domain.repositories;


import com.api.placeup.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product, Integer> {
}
