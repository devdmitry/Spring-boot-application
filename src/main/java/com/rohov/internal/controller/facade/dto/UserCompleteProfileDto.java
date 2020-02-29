package com.rohov.internal.controller.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCompleteProfileDto {

    @NotEmpty(message = "Required field name")
    String name;

    @NotEmpty(message = "Required field password")
    String password;

    @NotEmpty(message = "Required field position")
    String position;

    @NotEmpty(message = "Required field token")
    String token;

}
