package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Products;
import com.api.placeup.domain.repositories.Sellers;
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

import javax.swing.*;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private AttachmentService attachmentService;
    private Products productsRepository;
    private Sellers sellersRepository;

    public AttachmentController(AttachmentService attachmentService,
                                Products productsRepository,
                                Sellers sellersRepository) {
        this.attachmentService = attachmentService;
        this.productsRepository = productsRepository;
        this.sellersRepository = sellersRepository;
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

    @PostMapping("/seller/{id}")
    public AttachmentDTO uploadSellerFile(@RequestParam("file")MultipartFile file, @PathVariable Integer id ) throws Exception {
        Seller seller = sellersRepository
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

        seller.setId(seller.getId());
        seller.setImageLink(downloadURl);
        sellersRepository.save(seller);

        return new AttachmentDTO(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> visualizeFile(@PathVariable Integer id) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(id);
        byte[] data = attachment.getData();
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .body(new ByteArrayResource(attachment.getData()));
    }

    @GetMapping("/download/{id}")
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
