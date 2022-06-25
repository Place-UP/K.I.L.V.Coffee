package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Emails extends JpaRepository<Email, Integer> {
}
