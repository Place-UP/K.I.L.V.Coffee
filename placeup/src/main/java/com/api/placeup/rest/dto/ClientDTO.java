package com.api.placeup.rest.dto;

import com.api.placeup.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Integer client;

    @NotEmpty(message = "{field.name.obligatory}")
    private String name;

    @NotEmpty(message = "{field.email.obligatory}")
    @Email(message = "{input.email.valid}")
    private String email;

    @NotEmpty(message = "{field.phone.obligatory}")
    private String phone;

    @NotEmpty(message = "{field.password.obligatory}")
    private String password;

    private User user;
}
