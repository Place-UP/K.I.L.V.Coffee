package com.ms.emailSend.models;

import com.ms.emailSend.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table( name = "tb_email" )
public class EmailModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID emailId;

    private String ownerRef;

    private String emailFrom;

    private String emailTo;

    private String subject;

    @Column( columnDefinition = "TEXT" )
    private String text;

    private LocalDateTime sendDateEmail;

    private StatusEmail statusEmail;
}
