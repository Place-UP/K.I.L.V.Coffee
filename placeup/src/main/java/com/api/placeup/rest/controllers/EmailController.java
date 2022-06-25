package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Email;
import com.api.placeup.rest.dto.EmailDTO;
import com.api.placeup.services.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
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
    public Email sendingEmail(@RequestBody @Valid EmailDTO dto) {
        Email email = new Email();
        BeanUtils.copyProperties(dto, email);
        service.sendEmail(email);
        return email;
    }
}
