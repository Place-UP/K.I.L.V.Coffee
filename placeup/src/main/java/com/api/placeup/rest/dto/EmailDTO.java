package com.ms.emailSend.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailDTO {

    @NotBlank
    private String ownerRef;

    @Email
    @NotBlank
    private String emailFrom;

    @Email
    @NotBlank
    private String emailTo;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;

}
