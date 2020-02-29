package com.rohov.internal.controller.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResetPasswordDto {

    @NotEmpty(message = "Missed required field 'email'")
    @Email(message = "Email should be valid")
    String email;

}
