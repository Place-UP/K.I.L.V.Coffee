package com.api.placeup.rest.dto;

import com.api.placeup.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDTO {

    private Integer sellerId;

    @NotEmpty(message = "{field.name.obligatory}")
    private String name;

    @NotEmpty(message = "{field.email.obligatory}")
    @Email(message = "{input.email.valid}")
    private String email;

    @NotEmpty(message = "{field.cnpj.obligatory}")
    @CNPJ(message = "{input.cnpj.valid}")
    private String cnpj;

    @NotEmpty(message = "{field.phone.obligatory}")
    private String phone;

    @NotEmpty(message = "{field.password.obligatory}")
    private String password;

    private Boolean mute;
    private Boolean blind;
    private Boolean wheelchair;
    private Boolean deaf;

    private Integer address;

    @NotEmpty(message = "{field.state.obligatory}")
    private String state;

    @NotEmpty(message = "{field.city.obligatory}")
    private String city;

    @NotEmpty(message = "{field.district.obligatory}")
    private String district;

    @NotEmpty(message = "{field.street.obligatory}")
    private String street;

    @NotEmpty(message = "{field.houseNumber.obligatory}")
    private String houseNumber;

    @NotEmpty(message = "{field.cep.obligatory}")
    private String cep;


}
