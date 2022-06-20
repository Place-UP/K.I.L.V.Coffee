package com.api.placeup.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationItemDTO {

    @NotEmpty(message = "{field.product.obligatory}")
    private Integer product;

    @NotEmpty(message = "{field.quantity.obligatory}")
    private Integer quantity;
}