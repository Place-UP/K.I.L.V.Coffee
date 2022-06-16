package com.api.placeup.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer client;
    private Integer seller;
    private BigDecimal total;
    private List<ReservationItemDTO> items;
}