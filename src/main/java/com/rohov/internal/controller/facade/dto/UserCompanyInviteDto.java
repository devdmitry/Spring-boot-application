package com.rohov.internal.controller.facade.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UserCompanyInviteDto {

    @NotNull(message = "Required field email !")
    String email;

    @NotNull(message = "Required field roles")
    List<RoleDto> roles;

}
