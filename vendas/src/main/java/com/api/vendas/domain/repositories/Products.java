package com.api.vendas.domain.repositories;


import com.api.vendas.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product, Integer> {
}
