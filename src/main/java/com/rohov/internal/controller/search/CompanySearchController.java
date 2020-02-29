package com.rohov.internal.controller.search;

import com.rohov.internal.controller.facade.CompanyFacade;
import com.rohov.internal.controller.facade.dto.CompanyDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;


@Api(tags = "company_search")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/companies/search")
public class CompanySearchController {

    CompanyFacade companyFacade;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "String", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "String", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "String", paramType = "query")
    })
    @GetMapping(params = "value")
    public ResponseEntity<Set<CompanyDto>> findByNameOrDescription(@RequestParam String value,
                                                                   Pageable pageable) {
        return ResponseEntity.ok(companyFacade.findByNameOrDescription(value, pageable));
    }


}
