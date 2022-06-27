package com.api.placeup.services.impl;


import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.domain.entities.Product;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Attachments;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final Attachments attachments;
    private final Products productsRepository;
    private final Sellers sellersRepository;

    @Override
    public AttachmentDTO uploadProductFile(MultipartFile file, Integer id) throws Exception {
        Product product = productsRepository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));

        Attachment attachment = null;
        String downloadURl = "";
        attachment = saveAttachment(file);
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

    @Override
    public AttachmentDTO uploadSellerFile(MultipartFile file, Integer id ) throws Exception {
        Seller seller = sellersRepository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product not found."));

        Attachment attachment = null;
        String downloadURl = "";
        attachment = saveAttachment(file);
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

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());
            return attachments.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(Integer fileId) throws Exception {
        return attachments
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }

    @Override
    public ResponseEntity<Resource> visualizeFile( Integer id) throws Exception {
        Attachment attachment = null;
        attachment = getAttachment(id);
        byte[] data = attachment.getData();
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .body(new ByteArrayResource(attachment.getData()));
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Integer id) throws Exception {
        Attachment attachment = null;
        attachment = getAttachment(id);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
