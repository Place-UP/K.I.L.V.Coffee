package com.api.placeup.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @Column(name = "password", length = 10)
    private String password;

    //Accessibility
    @Column(name = "mute")
    private Boolean mute;

    @Column(name = "blind")
    private Boolean blind;

    @Column(name = "wheelchair")
    private Boolean wheelchair;

    @Column(name = "deaf")
    private Boolean deaf;

    //Product
    @JsonIgnore
    @OneToMany( mappedBy = "seller" , fetch = FetchType.LAZY )
    private Set<Product> products;

<<<<<<< HEAD
    //Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;
=======
    @OneToMany( mappedBy = "seller" , fetch = FetchType.LAZY )
    private Set<Reservation> reservations;
>>>>>>> 7b11c01f0659dd798c9aa6d2e5e1f196cc36dfb7
}