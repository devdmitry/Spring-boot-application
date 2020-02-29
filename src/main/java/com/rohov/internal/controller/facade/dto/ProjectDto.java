package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Getter
@Builder
public class ProjectDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotEmpty(message = "Missing required field 'name'")
    String name;

    @NotEmpty(message = "Missing required field 'description'")
    String description;

    @ApiModelProperty(hidden = true)
    Instant createdDate;

    @ApiModelProperty(hidden = true)
    Instant updatedDate;

}
