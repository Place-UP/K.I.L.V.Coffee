package com.api.placeup.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationReservationDTO {
    private Integer code;
    private String email;
    private String nameClient;
    private BigDecimal total;
    private String dateReservation;
    private String status;
    private List<InformationItemReservationDTO> items;
}