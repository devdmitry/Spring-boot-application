package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.UserResponseDto;
import com.rohov.internal.entity.User;
import com.rohov.internal.security.SecurityConstants;
import com.rohov.internal.security.TokenManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class UserResponseMapper {

    TokenManager tokenManager;
    UserMapper userMapper;

    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .userDto(userMapper.toDto(user))
                .accessToken(tokenManager.createToken(user, SecurityConstants.activeTokenExpiring,
                        SecurityConstants.Type.AUTHENTICATE))
                .build();
    }
}
