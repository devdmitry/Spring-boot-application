package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Builder
public class CompanyDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotEmpty(message = "Missing required field 'name'")
    String name;

    String description;

    @ApiModelProperty(hidden = true)
    UserDto owner;

    @ApiModelProperty(hidden = true)
    Date createdDate;

    @ApiModelProperty(hidden = true)
    Date updatedDate;

}
