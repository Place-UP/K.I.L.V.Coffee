package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Email;
import com.api.placeup.domain.enums.StatusEmail;
import com.api.placeup.domain.repositories.Emails;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Emails repository;

    private final JavaMailSender mailSender;

    public Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            mailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
        } catch ( MailException e ) {
            email.setStatusEmail(StatusEmail.ERROR);
        }

        return repository.save(email);

    }

}
