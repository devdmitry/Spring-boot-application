package com.rohov.internal.controller.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Builder
public class TaskDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotEmpty(message = "Required field name")
    String name;

    @NotEmpty(message = "Required field description")
    String description;

    @NotNull(message = "Required field status")
    TaskStatusDto status;

    @ApiModelProperty(hidden = true)
    Set<TaskChecklistDto> checklist;

    @ApiModelProperty(hidden = true)
    Float completePercent;

    @ApiModelProperty(hidden = true)
    Date createdDate;

    @ApiModelProperty(hidden = true)
    Date updatedDate;

}
