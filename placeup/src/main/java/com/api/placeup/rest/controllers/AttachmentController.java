package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.rest.dto.AttachmentDTO;
import com.api.placeup.services.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;

@RestController
@RequestMapping("/api/imagens")
public class AttachmentController {

    private AttachmentService service;

    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public AttachmentDTO uploadFile(@RequestParam("file") MultipartFile file ) {
        Attachment attachment = null;
        String downloadUrl = "";
        attachment = service.saveAttachment(file);
        downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(String.valueOf(attachment.getId()))
                .toUriString();

        return new AttachmentDTO(
                attachment.getFileName(),
                downloadUrl,
                file.getContentType(),
                file.getSize()
        );
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
        Attachment attachment = null;
        attachment = service.getAttachment(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        attachment.getFileName() + "\"").body(new ByteArrayResource(attachment.getData()));
    }
}
