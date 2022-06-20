package com.api.placeup.services;

import com.api.placeup.domain.entities.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file);

    Attachment getAttachment(Integer id);
}
