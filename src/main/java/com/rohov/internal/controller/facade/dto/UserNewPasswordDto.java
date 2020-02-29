package com.rohov.internal.controller.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserNewPasswordDto {

    @NotEmpty(message = "Missed required field 'Reset Token'")
    String resetToken;

    @NotEmpty(message = "Missed required field 'New password'")
    String newPassword;

}
