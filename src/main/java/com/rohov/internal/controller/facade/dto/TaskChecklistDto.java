package com.rohov.internal.controller.facade.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskChecklistDto {

    @ApiModelProperty(hidden = true)
    Long id;

    String value;

    @ApiModelProperty(hidden = true)
    Boolean isDone;

}
