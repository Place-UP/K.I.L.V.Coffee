package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Attachment;
import com.api.placeup.domain.repositories.Attachments;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.services.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private Attachments repository;

    @Override
    public Attachment saveAttachment(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new BusinessRuleException("Filename contains invalid path sequence: " + fileName);
            }
            Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes());
            return repository.save(attachment);
        } catch (Exception e) {
            throw new BusinessRuleException("Could not save file: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("File not found with id: " + id));

    }
}
