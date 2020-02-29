package com.rohov.internal.controller.facade.impl;

import com.rohov.internal.controller.facade.EmployeePositionFacade;
import com.rohov.internal.controller.facade.dto.EmployeePositionDto;
import com.rohov.internal.controller.facade.mapper.EmployeePositionMapper;
import com.rohov.internal.service.EmployeePositionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class EmployeePositionFacadeImpl implements EmployeePositionFacade {

    EmployeePositionService employeePositionService;

    EmployeePositionMapper employeePositionMapper;

    @Override
    public List<EmployeePositionDto> find(Pageable pageable) {
        return employeePositionService.find(pageable)
                .stream()
                .map(employeePositionMapper::toDto)
                .collect(Collectors.toList());
    }

}


