package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.rest.dto.AttachmentDTO;
import com.api.placeup.services.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService service;


    @PostMapping("/product/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AttachmentDTO uploadProductFile(@RequestParam("file")MultipartFile file, @PathVariable Integer id ) throws Exception {
        return service.uploadProductFile(file, id);
    }

    @PostMapping("/seller/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AttachmentDTO uploadSellerFile(@RequestParam("file")MultipartFile file, @PathVariable Integer id ) throws Exception {
        return service.uploadSellerFile(file, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> visualizeFile(@PathVariable Integer id) throws Exception {
        return service.visualizeFile(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) throws Exception {
        return service.downloadFile(id);
    }
}
