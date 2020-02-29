package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.RoleDto;
import com.rohov.internal.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public RoleDto toDto(Permission permission) {
        return RoleDto.builder()
                .id(permission.getId())
                .name(permission.getName())
                .build();
    }

    public Permission toEntity(RoleDto roleDto) {
        return new Permission(roleDto.getName());
    }

}
