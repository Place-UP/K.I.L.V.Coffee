package com.api.placeup.services;

import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.rest.dto.AttachmentDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    AttachmentDTO uploadProductFile(MultipartFile file, Integer id ) throws Exception;
    AttachmentDTO uploadSellerFile(@RequestParam("file")MultipartFile file, Integer id ) throws Exception;
    Attachment getAttachment(Integer fileId) throws Exception;
    ResponseEntity<Resource> visualizeFile(Integer id) throws Exception;
    ResponseEntity<Resource> downloadFile(Integer id) throws Exception;
}
