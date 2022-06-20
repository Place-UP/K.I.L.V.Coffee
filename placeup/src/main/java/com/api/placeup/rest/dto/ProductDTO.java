package com.api.placeup.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "{field.seller.obligatory}")
    private Integer seller;

    @NotEmpty(message = "{field.description.obligatory}")
    private String description;

    @NotNull(message = "{field.price.obligatory}")
    private BigDecimal price;
}
