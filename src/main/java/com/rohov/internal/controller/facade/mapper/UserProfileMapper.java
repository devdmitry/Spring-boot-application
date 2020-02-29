package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.UserProfileDto;
import com.rohov.internal.entity.UserProfile;
import com.rohov.internal.service.EmployeePositionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class UserProfileMapper {

    EmployeePositionService employeePositionService;

    EmployeePositionMapper employeePositionMapper;

    public UserProfileDto toDto(UserProfile userProfile) {
        if (Objects.isNull(userProfile)) {
            return null;
        }
        return UserProfileDto.builder()
                .id(userProfile.getId())
                .name(userProfile.getName())
                .employeePositionDto(employeePositionMapper.toDto(userProfile.getEmployeePosition()))
                .build();
    }

    public UserProfile toEntity(UserProfileDto userProfileDto) {
        UserProfile userProfile = new UserProfile();

        userProfile.setName(userProfileDto.getName());

        if (Objects.nonNull(userProfileDto.getEmployeePositionDto())) {
            userProfile.setEmployeePosition(
                    employeePositionService.find(userProfileDto.getEmployeePositionDto().getName())
            );
        }

        return userProfile;
    }

}
