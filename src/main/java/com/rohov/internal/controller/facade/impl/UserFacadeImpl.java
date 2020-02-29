package com.rohov.internal.controller.facade.impl;

import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.UserCompleteProfileDto;
import com.rohov.internal.controller.facade.dto.UserCredentialDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.controller.facade.dto.UserNewPasswordDto;
import com.rohov.internal.controller.facade.dto.UserResetPasswordDto;
import com.rohov.internal.controller.facade.dto.UserResponseDto;
import com.rohov.internal.controller.facade.mapper.UserMapper;
import com.rohov.internal.controller.facade.mapper.UserResponseMapper;
import com.rohov.internal.entity.User;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.UserCredentialException;
import com.rohov.internal.security.SecurityConstants;
import com.rohov.internal.service.impl.UserServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class  UserFacadeImpl implements UserFacade {

    UserServiceImpl userService;

    AuthenticationManager authenticationManager;

    UserMapper userMapper;
    UserResponseMapper userResponseMapper;

    @Override
    public UserDto getByToken(String token) {
        return userMapper.toDto(userService.getByToken(token));
    }

    @Override
    public UserDto create(UserDto userDto) {
        return userMapper.toDto(userService.create(userMapper.toEntity(userDto, true)));
    }

    @Override
    public UserDto completeProfile(UserCompleteProfileDto userCompleteProfileDto) {
        return userMapper.toDto(userService.completeProfile(userCompleteProfileDto.getToken(), userCompleteProfileDto.getPassword(),
                userCompleteProfileDto.getName(), userCompleteProfileDto.getPosition()));
    }

    @Override
    public void verificateUser(String token) {
        userService.verificateUser(token);
    }

    @Override
    public void sendResetToken(UserResetPasswordDto userResetPasswordDto) {
        userService.sendResetToken(userResetPasswordDto.getEmail());
    }

    @Override
    public UserResponseDto newPassword(UserNewPasswordDto userNewPasswordDto) {
        return userResponseMapper.toDto(
                userService.makeNewPassword(userNewPasswordDto.getResetToken(), userNewPasswordDto.getNewPassword())
        );
    }

    @Override
    public UserResponseDto signIn(UserCredentialDto userCredentialDto, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userCredentialDto.getEmail(), userCredentialDto.getPassword()
            ));

            User user = (User)userService.loadUserByUsername(userCredentialDto.getEmail());

            UserResponseDto userResponseDto = userResponseMapper.toDto(user);

            response.setHeader(SecurityConstants.TOKEN_HEADER,
                    SecurityConstants.TOKEN_PREFIX + userResponseDto.getAccessToken());

            return userResponseDto;
        } catch (AuthenticationException e) {
            throw new UserCredentialException(ErrorMessages.WRONG_CREDENTIAL);
        }
    }

}
