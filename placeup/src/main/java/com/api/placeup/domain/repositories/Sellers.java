package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Sellers extends JpaRepository<Seller, Integer> {
}
