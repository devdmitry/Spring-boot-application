package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.CompanyFacade;
import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.CompanyDto;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "company")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    CompanyFacade companyFacade;
    UserFacade userFacade;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", dataType = "String", paramType = "header", required = true),
            @ApiImplicitParam(name = "page", dataType = "String", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "String", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "String", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(companyFacade.find(pageable));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", dataType = "String", paramType = "header", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(companyFacade.find(id));
    }

    @GetMapping("/owned")
    public ResponseEntity<List<CompanyDto>> getOwnedCompanies(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token) {
        return ResponseEntity.ok(companyFacade.findOwned(userFacade.getByToken(token).getId()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<CompanyDto>> getMyCompanies(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token) {
        return ResponseEntity.ok(companyFacade.findMy(userFacade.getByToken(token).getId()));
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @Valid @RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyFacade.create(userFacade.getByToken(token).getId(), companyDto));
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDto> update(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable("companyId") long companyId, @Valid @RequestBody CompanyDto companyDto) {
        if (isOwner(token, companyId)) {
            return ResponseEntity.ok(companyFacade.update(companyId, companyDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyDto> patch(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable("companyId") long companyId, @RequestBody CompanyDto companyDto) {
        if (isOwner(token, companyId)) {
            return ResponseEntity.ok(companyFacade.patch(companyId, companyDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity delete(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable("companyId") Long companyId) {
        if (isOwner(token, companyId)) {
            companyFacade.delete(companyId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isOwner(String token, Long companyId) {
        return userFacade.getByToken(token).getId().equals(companyFacade.find(companyId).getOwner().getId());
    }

}
