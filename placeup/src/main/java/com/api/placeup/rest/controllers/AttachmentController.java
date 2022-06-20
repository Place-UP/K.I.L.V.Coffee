package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.rest.dto.AttachmentDTO;
import com.api.placeup.services.AttachmentService;
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

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private AttachmentService attachmentService;
    private Products productsRepository;

    public AttachmentController(AttachmentService attachmentService, Products productsRepository) {
        this.attachmentService = attachmentService;
        this.productsRepository = productsRepository;
    }

    @PostMapping("/product/{id}")
    public AttachmentDTO uploadProductFile(@RequestParam("file")MultipartFile file, @PathVariable Integer id ) throws Exception {
        Product product = productsRepository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));

        Attachment attachment = null;
        String downloadURl = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/attachments/")
                .path(String.valueOf(attachment.getId()))
                .toUriString();

        product.setId(product.getId());
        product.setImageLink(downloadURl);
        productsRepository.save(product);

        return new AttachmentDTO(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(id);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
