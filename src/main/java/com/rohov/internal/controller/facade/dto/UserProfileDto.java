package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UserProfileDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotNull(message = "Missing required property 'name")
    String name;

    @NotNull(message = "Missing required property 'position'")
    EmployeePositionDto employeePositionDto;

}
