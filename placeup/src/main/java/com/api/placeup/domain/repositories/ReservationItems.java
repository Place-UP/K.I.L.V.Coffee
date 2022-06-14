package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItems extends JpaRepository<ReservationItem, Integer> {
}