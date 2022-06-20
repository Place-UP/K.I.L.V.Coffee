package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Clients extends JpaRepository<Client, Integer > {

    @Query(" select c from Client c where c.name like %:name ")
    Optional<Client> findByName(@Param("name") String name);

    @Query(" select c from Client c where c.email like %:email ")
    Optional<Client> findByEmail(@Param("email") String email);

    @Query(" select c from Client c left join fetch c.reservations where c.id = :id  ")
    Client findClientFetchReservation( @Param("id") Integer id );


}