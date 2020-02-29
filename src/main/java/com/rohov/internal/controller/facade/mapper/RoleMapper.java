package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.RoleDto;
import com.rohov.internal.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role toEntity(RoleDto roleDto) {
        return new Role(roleDto.getName());
    }

}
