package com.api.placeup.rest.dto;

import com.api.placeup.domain.entities.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    private Integer id;

    private Seller seller;

    @NotEmpty(message = "{field.description.obligatory}")
    private String description;

    @NotNull(message = "{field.price.obligatory}")
    private BigDecimal price;

}
