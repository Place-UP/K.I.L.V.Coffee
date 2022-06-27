package com.api.placeup.domain.entities;

import com.api.placeup.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NotEmpty(message = "{field.login.obligatory}")
    private String login;

    @JsonIgnore
    @Column
    @NotEmpty(message = "{field.password.obligatory}")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserType userType;

}