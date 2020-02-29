package com.rohov.internal.controller.facade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    UserDto userDto;

    String accessToken;

}
