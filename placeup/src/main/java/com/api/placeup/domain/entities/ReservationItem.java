package com.api.placeup.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation_item")
public class ReservationItem  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reservation")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @Column
    private Integer quantity;


}