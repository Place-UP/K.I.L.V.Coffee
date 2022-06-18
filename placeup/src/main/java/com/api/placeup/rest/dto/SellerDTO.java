package com.api.placeup.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDTO {

    private Integer seller;
    private String name;
    private String email;
    private String cnpj;
    private String phone;
    private String password;
    private Boolean mute;
    private Boolean blind;
    private Boolean wheelchair;
    private Boolean deaf;

    private Integer address;
    private String state;
    private String city;
    private String district;
    private String street;
    private String houseNumber;

}
