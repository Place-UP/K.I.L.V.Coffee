package com.api.placeup.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {
    private String fileName;
    private String downloadURl;
    private String fileType;
    private long fileSize;
}
