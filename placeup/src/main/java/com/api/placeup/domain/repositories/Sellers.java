package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Sellers extends JpaRepository<Seller, Integer> {

    @Query(" select s from Seller s left join fetch s.address where s.id = :id ")
    Optional<Seller> findByIdFetchAddress(@Param("id") Integer id);

    @Query(" select s from Seller s where s.cnpj like %:cnpj ")
    Optional<Seller> findByCnpj(@Param("cnpj") String cnpj);

    @Query(" select s from Seller s where s.email like %:email ")
    Optional<Seller> findByEmail(@Param("email") String email);

    @Query(" select s from Seller s where s.name like %:name ")
    Optional<Seller> findByName(@Param("name") String name);

    @Query(" select s from Seller s where s.phone like %:phone ")
    Optional<Seller> findByPhone(@Param("phone") String phone);
}
