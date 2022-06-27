package com.api.placeup.rest.dto;

import com.api.placeup.validations.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    @NotNull(message = "{field.client.obligatory}")
    private Integer client;

    @NotNull(message = "{field.seller.obligatory}")
    private Integer seller;

    @NotNull
    private LocalDateTime withdrawalDate;

    private BigDecimal total;

    @NotEmptyList
    private List<ReservationItemDTO> items;
}