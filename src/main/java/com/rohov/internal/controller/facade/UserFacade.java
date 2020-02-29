package com.rohov.internal.controller.facade;

import com.rohov.internal.controller.facade.dto.UserCompleteProfileDto;
import com.rohov.internal.controller.facade.dto.UserCredentialDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.controller.facade.dto.UserNewPasswordDto;
import com.rohov.internal.controller.facade.dto.UserResetPasswordDto;
import com.rohov.internal.controller.facade.dto.UserResponseDto;

import javax.servlet.http.HttpServletResponse;

public interface UserFacade {

    UserDto getByToken(String token);

    UserDto create(UserDto userDto);

    UserDto completeProfile(UserCompleteProfileDto userCompleteProfileDto);

    void verificateUser(String token);

    void sendResetToken(UserResetPasswordDto userResetPasswordDto);

    UserResponseDto newPassword(UserNewPasswordDto userNewPasswordDto);

    UserResponseDto signIn(UserCredentialDto userCredentialDto, HttpServletResponse response);

}
