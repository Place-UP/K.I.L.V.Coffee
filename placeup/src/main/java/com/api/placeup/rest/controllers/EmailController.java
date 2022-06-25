package com.ms.emailSend.controllers;

import com.ms.emailSend.dtos.EmailDTO;
import com.ms.emailSend.models.EmailModel;
import com.ms.emailSend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService service;

    @PostMapping("/sending-email")
    @ResponseStatus( HttpStatus.CREATED )
    public EmailModel sendingEmail(@RequestBody @Valid EmailDTO dto) {
        EmailModel email = new EmailModel();
        BeanUtils.copyProperties(dto, email);
        service.sendEmail(email);
        return email;
    }
}
