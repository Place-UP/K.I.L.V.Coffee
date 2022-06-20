package com.api.placeup.domain.repositories;

import com.api.placeup.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Attachments extends JpaRepository<Attachment, Integer> {
}
