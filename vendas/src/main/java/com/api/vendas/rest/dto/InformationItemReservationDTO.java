package com.api.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationItemReservationDTO {
    private String productDescription;
    private BigDecimal priceUnitary;
    private Integer quantity;
}