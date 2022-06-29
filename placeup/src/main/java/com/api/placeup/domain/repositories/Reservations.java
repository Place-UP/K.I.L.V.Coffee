package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Reservations extends JpaRepository<Reservation, Integer> {

    @Query(" select r from Reservation r where r.client.id = :id" )
    List<Reservation> findByClient(@Param("id") Integer id);

    @Query(" select r from Reservation r where r.seller.id = :id" )
    List<Reservation> findBySeller(@Param("id") Integer id);

    @Query(" select p from Reservation p left join fetch p.items where p.id = :id ")
    Optional<Reservation> findByIdFetchItems(@Param("id") Integer id);
}