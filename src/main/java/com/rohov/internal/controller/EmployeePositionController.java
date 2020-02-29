package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.EmployeePositionFacade;
import com.rohov.internal.controller.facade.dto.EmployeePositionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "employee-position")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/employee-positions")
public class EmployeePositionController {

    EmployeePositionFacade employeePositionFacade;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "String", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "String", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "String", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<List<EmployeePositionDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(employeePositionFacade.find(pageable));
    }

}
