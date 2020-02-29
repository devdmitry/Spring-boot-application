package com.rohov.internal.controller.facade;

import com.rohov.internal.controller.facade.dto.EmployeePositionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeePositionFacade {

    List<EmployeePositionDto> find(Pageable pageable);

}
