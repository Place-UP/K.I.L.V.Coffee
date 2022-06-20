package com.api.placeup.domain.repositories;


import com.api.placeup.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Attachments extends JpaRepository<Attachment, Integer> {
}