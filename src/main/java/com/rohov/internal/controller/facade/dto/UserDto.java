package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class UserDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotNull(message = "Missing required User Profile Information")
    UserProfileDto userProfileDto;

    @Email
    @NotEmpty(message = "Missing required field 'email'")
    String email;

    @NotEmpty(message = "Missing required field 'password'")
    String password;

    @NotEmpty(message = "Missing required field roles")
    List<RoleDto> roles;

    @ApiModelProperty(hidden = true)
    Date createdDate;

    @ApiModelProperty(hidden = true)
    Date updatedDate;

    @Override
    public int hashCode() {
        return this.getId() == null ? -1 : this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((UserDto)obj).getId());
    }
}

