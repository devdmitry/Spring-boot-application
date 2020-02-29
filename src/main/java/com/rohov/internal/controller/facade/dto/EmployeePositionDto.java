package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;


@Getter
@Builder
public class EmployeePositionDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotEmpty(message = "Missing required field 'name'")
    String name;

}
