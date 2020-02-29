package com.rohov.internal.controller.facade.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;


@Getter
public class UserCredentialDto {

    @NotEmpty(message = "Missed required field 'email'")
    String email;

    @NotEmpty(message = "Missed required field 'password'")
    String password;
}
