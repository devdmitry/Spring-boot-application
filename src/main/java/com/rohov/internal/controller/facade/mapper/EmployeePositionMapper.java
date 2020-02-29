package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.EmployeePositionDto;
import com.rohov.internal.entity.EmployeePosition;
import org.springframework.stereotype.Component;

@Component
public class EmployeePositionMapper {

    public EmployeePositionDto toDto(EmployeePosition employeePosition) {
        return EmployeePositionDto.builder()
                .id(employeePosition.getId())
                .name(employeePosition.getName())
                .build();
    }

    public EmployeePosition toEntity(EmployeePositionDto employeePositionDto) {
        EmployeePosition employeePosition = new EmployeePosition();

        employeePosition.setId(employeePositionDto.getId());
        employeePosition.setName(employeePositionDto.getName());

        return employeePosition;
    }
}
