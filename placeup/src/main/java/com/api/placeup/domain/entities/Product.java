package com.api.placeup.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "products" )
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "description" )
    private String description;

    @Column( name = "price" )
    private BigDecimal price;

    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

}