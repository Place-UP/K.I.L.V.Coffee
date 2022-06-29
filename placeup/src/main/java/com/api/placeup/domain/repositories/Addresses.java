package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Addresses extends JpaRepository<Address, Integer> {
}
