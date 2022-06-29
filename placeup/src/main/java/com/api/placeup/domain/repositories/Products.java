package com.api.placeup.domain.repositories;


import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Products extends JpaRepository<Product, Integer> {

    @Query(" select p from Product p where p.seller.id = :id")
    List<Product> findBySeller(@Param("id") Integer id);
}
