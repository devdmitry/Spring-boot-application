package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
public class UserMapper {

    UserProfileMapper userProfileMapper;
    RoleMapper roleMapper;

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userProfileDto(userProfileMapper.toDto(user.getUserProfile()))
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(roleMapper::toDto).collect(Collectors.toList()))
                .createdDate(user.getCreated_date())
                .updatedDate(user.getUpdated_date())
                .build();
    }

    public User toEntity(UserDto userDto, boolean isCreate) {
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        if (!userDto.getRoles().isEmpty()) {
            user.setRoles(userDto.getRoles().stream().map(roleMapper::toEntity).collect(Collectors.toSet()));
        }

        if (Objects.nonNull(userDto.getUserProfileDto())) {
            user.setUserProfile(userProfileMapper.toEntity(userDto.getUserProfileDto()));
        }

        if (!isCreate) {
            user.setCreated_date(userDto.getCreatedDate());
            user.setId(userDto.getId());
        }

        return user;
    }
}
