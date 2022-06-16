package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Client;
import com.api.placeup.domain.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Sellers extends JpaRepository<Seller, Integer> {

    @Query(value = " select * from seller s where s.name like '%:name%' ", nativeQuery = true)
    List<Seller> findByName(@Param("name") String name );

    @Query(" delete from Seller s where s.name =:name ")
    @Modifying
    void deleteByName(String name);

    boolean existsByName(String name);

//    @Query(" select s from Seller s left join fetch s.products where s.id = :id  ")
//    Client findProductSeller(@Param("id") Integer id );
}
