package com.api.placeup.domain.entities;

import com.api.placeup.domain.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table( name = "tb_email" )
public class Email implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer emailId;

    private String ownerRef;

    private String emailFrom;

    private String emailTo;

    private String subject;

    @Column( columnDefinition = "TEXT" )
    private String text;

    private LocalDateTime sendDateEmail;

    private StatusEmail statusEmail;
}
