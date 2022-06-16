package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Sellers extends JpaRepository<Seller, Integer> {

    @Query(" select s from Seller s left join fetch s.address where s.id = :id ")
    Optional<Seller> findByIdFetchAddress(@Param("id") Integer id);
}
