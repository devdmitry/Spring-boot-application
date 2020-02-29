package com.rohov.internal.service.impl;

import com.rohov.internal.entity.EmployeePosition;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.EmployeePositionRepository;
import com.rohov.internal.service.EmployeePositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmployeePositionServiceImpl implements EmployeePositionService {

    EmployeePositionRepository employeePositionRepository;

    @Override
    public List<EmployeePosition> find(Pageable pageable) {
        return employeePositionRepository
                .findAll(pageable)
                .getContent();
    }

    @Override
    public EmployeePosition find(Long id) {
        final String errorMsg = String.format(ErrorMessages.EMPLOYEE_POSITION_WRONG_ID, id);
        return employeePositionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(errorMsg));
    }

    @Override
    public EmployeePosition find(String name) {
        final String errorMsg = String.format(ErrorMessages.EMPLOYEE_POSITION_WRONG_NAME, name);
        return employeePositionRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException(errorMsg));
    }
}
