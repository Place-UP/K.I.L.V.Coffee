package com.api.placeup.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "products" )
public class Product extends RepresentationModel<Product>{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id" )
    private Integer id;

    @Column( name = "description" )
    private String description;

    @Column( name = "price" )
    private BigDecimal price;

    @Column( name = "image_link")
    private String imageLink;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

}