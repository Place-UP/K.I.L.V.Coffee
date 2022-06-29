package com.api.placeup.rest.dto;

import com.api.placeup.domain.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private String withdrawalDate;
    private String status;
    private String nameSeller;
    private List<InformationItemReservationDTO> items;
}