package com.rohov.internal.service;

import com.rohov.internal.entity.EmployeePosition;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface EmployeePositionService {

    List<EmployeePosition> find(Pageable pageable);

    EmployeePosition find(Long id);

    EmployeePosition find(String name);

}
