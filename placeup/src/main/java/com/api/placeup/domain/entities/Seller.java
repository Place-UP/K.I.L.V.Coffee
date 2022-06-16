package com.api.placeup.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "seller" )
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "phone", length = 11)
    private String phone;

    //Accessibility
    @Column(name = "mute")
    private Boolean mute;

    @Column(name = "blind")
    private Boolean blind;

    @Column(name = "wheelchair")
    private Boolean wheelchair;

    @Column(name = "deaf")
    private Boolean deaf;
}