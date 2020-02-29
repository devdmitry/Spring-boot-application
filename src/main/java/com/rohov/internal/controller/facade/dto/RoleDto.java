package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotEmpty(message = "Required field name")
    String name;

}
