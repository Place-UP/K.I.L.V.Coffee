package com.ms.emailSend.services;

import com.ms.emailSend.enums.StatusEmail;
import com.ms.emailSend.models.EmailModel;
import com.ms.emailSend.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository repository;

    private final JavaMailSender mailSender;

    public EmailModel sendEmail(EmailModel email) {
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
